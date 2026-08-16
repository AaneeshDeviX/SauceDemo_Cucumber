FROM maven:3.9-eclipse-temurin-17

# Chrome + the libraries headless Selenium needs. WebDriverManager resolves the
# matching driver at run time, so no chromedriver is baked into the image.
RUN apt-get update \
 && apt-get install -y --no-install-recommends wget gnupg ca-certificates fonts-liberation \
 && wget -q -O /tmp/chrome.deb https://dl.google.com/linux/direct/google-chrome-stable_current_amd64.deb \
 && apt-get install -y --no-install-recommends /tmp/chrome.deb \
 && rm -rf /tmp/chrome.deb /var/lib/apt/lists/*

WORKDIR /framework

COPY pom.xml .
RUN mvn -B -q dependency:go-offline

COPY src ./src

# Override to run a subset, e.g.
#   docker run --rm saucedemo-tests mvn test -Dcucumber.filter.tags="@smoke"
CMD ["mvn", "-B", "test", "-Dheadless=true", "-Drecord.video=false"]
