# SauceDemo — Selenium + Cucumber + TestNG

[![Build](https://github.com/AaneeshDeviX/SauceDemo_Cucumber/actions/workflows/ci.yml/badge.svg)](https://github.com/AaneeshDeviX/SauceDemo_Cucumber/actions/workflows/ci.yml)

BDD UI suite against [SauceDemo](https://www.saucedemo.com) — **47 scenarios**
across 7 feature files, built on the Page Object Model with a shared abstract
`BasePage`. Failures capture a screenshot; the whole run can be screen-recorded.

## Current status

**26 of 51 scenario runs pass; 25 fail against the live site.** The application
changed after these tests were written, and two locators no longer match:

| Locator | Problem |
|---|---|
| `button[id^='add-to-cart']` in `InventoryPage` | SauceDemo moved these buttons to `data-test` attributes. Nothing is added to the cart, so every cart, checkout and product-detail scenario fails downstream — empty list (`IndexOutOfBoundsException`), missing remove button, checkout `finish` button never appears. |
| `[data-test='open-menu']` in `InventoryPage` | Resolves to an `<img>` that sits under `#react-burger-menu-btn`, so the click is intercepted (`ElementClickInterceptedException`). |

Two locator changes account for all 25 failures. The badge above tracks
**compilation only**; the full suite runs on demand via the
*UI Tests (full suite)* workflow and is deliberately kept off the push path so
this repository never shows a green badge over a failing suite.

This is ordinary automation maintenance — a suite pinned to a third-party demo
site drifts when that site changes. It is recorded here rather than hidden.

## Stack

| | |
|---|---|
| Browser automation | Selenium 4.27.0 |
| BDD | Cucumber 7.20.1 (Java) |
| Runner | TestNG 7.10.2 |
| Driver management | WebDriverManager 5.9.2 |
| Reporting | ExtentReports 5.1.2 via the Cucumber 7 adapter |
| Logging | Log4j2 2.24.3 |
| Capture | Monte screen recorder, custom screenshot util |
| CI | GitHub Actions — compile gate, then headless Chrome run |

## Running it

```bash
mvn test                                       # all 47 scenarios
mvn test -Dcucumber.filter.tags="@smoke"       # smoke only
```

Containerised, with Chrome already inside the image:

```bash
docker build -t saucedemo-tests .
docker run --rm saucedemo-tests
```

## Coverage

| Feature | Scenarios | Covers |
|---|---|---|
| `checkout.feature` | 11 | full purchase flow, field validation, totals and tax, cancel paths |
| `inventory.feature` | 11 | product grid, sorting by name and price, add and remove from cart |
| `login.feature` | 7 | all six SauceDemo user types, locked-out and invalid credentials |
| `cart.feature` | 6 | add, remove, badge count, persistence across navigation |
| `defects.feature` | 4 | known SauceDemo bugs, pinned so regressions stay visible |
| `navigation.feature` | 4 | burger menu, about, reset app state, logout |
| `product_detail.feature` | 4 | detail page content, back navigation, add to cart from detail |

`login.feature` is a Scenario Outline driven by an Examples table covering the
`standard`, `locked_out`, `problem`, `performance_glitch`, `error` and `visual`
users — the same steps, six data sets.

`defects.feature` deliberately encodes SauceDemo's known broken behaviour so the
suite documents defects rather than silently working around them.

## Layout

```
src/main/java/com/saucedemo/
  pages/          # BasePage + 9 page objects — all locators live here
  utils/          # DriverFactory, ScreenshotUtil, ScreenRecorderUtil
  config/         # ConfigReader
src/test/java/com/saucedemo/
  stepdefinitions/  # 6 step classes, one per feature area
  runners/          # TestRunner (full), SmokeTestRunner
  hooks/            # driver lifecycle, failure capture
src/test/resources/
  features/       # 7 .feature files
  config.properties
```

`BasePage` centralises the waiting strategy — explicit waits sourced from config,
`scrollIntoView` before every interaction, and clear-then-type — so individual page
objects stay free of timing code.

## Configuration

`src/test/resources/config.properties` sets the base URL, browser, headless flag
and wait timeouts, plus the SauceDemo user names. The passwords are SauceDemo's
published demo credentials (`secret_sauce`) for their public practice site — no
private credentials are stored in this repository.
