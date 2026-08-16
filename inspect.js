const puppeteer = require('puppeteer');
const delay = ms => new Promise(r => setTimeout(r, ms));

(async () => {
  const browser = await puppeteer.launch({headless:true,args:['--no-sandbox','--window-size=1920,1080']});
  const page = await browser.newPage();
  await page.setViewport({width:1920,height:1080});

  // Login
  await page.goto('https://www.saucedemo.com/', {waitUntil:'networkidle2'});
  await page.type('#user-name','standard_user');
  await page.type('#password','secret_sauce');
  await page.click('#login-button');
  await delay(3000);

  console.log('=== INVENTORY PAGE ===');
  console.log('URL:', page.url());
  const inv = await page.evaluate(() => {
    const dt = [...document.querySelectorAll('[data-test]')].map(e => e.getAttribute('data-test'));
    return {
      title: document.querySelector('.title')?.innerText,
      items: document.querySelectorAll('.inventory_item').length,
      addBtns: document.querySelectorAll('button[data-test^="add-to-cart"]').length,
      dataTest: [...new Set(dt)]
    };
  });
  console.log(JSON.stringify(inv, null, 2));

  // Add item and go to cart
  await page.evaluate(() => document.querySelector('button[data-test^="add-to-cart"]')?.click());
  await delay(1000);
  await page.evaluate(() => document.querySelector('.shopping_cart_link')?.click());
  await delay(2000);

  console.log('\n=== CART PAGE ===');
  console.log('URL:', page.url());
  const cart = await page.evaluate(() => {
    const dt = [...document.querySelectorAll('[data-test]')].map(e => ({
      tag: e.tagName, dataTest: e.getAttribute('data-test'), text: e.innerText?.substring(0,50)
    }));
    return dt;
  });
  console.log(JSON.stringify(cart, null, 2));

  // Click checkout
  const checkoutSel = await page.evaluate(() => {
    const el = document.querySelector('[data-test="checkout"]') || document.querySelector('#checkout');
    if (el) return {found: true, tag: el.tagName, id: el.id, dataTest: el.getAttribute('data-test')};
    // Search all buttons/links
    const btns = [...document.querySelectorAll('button,a')].filter(b => b.innerText.toLowerCase().includes('checkout'));
    return {found: false, matches: btns.map(b => ({tag:b.tagName, id:b.id, dt:b.getAttribute('data-test'), text:b.innerText}))};
  });
  console.log('\nCheckout element:', JSON.stringify(checkoutSel, null, 2));

  await page.evaluate(() => {
    const el = document.querySelector('[data-test="checkout"]') || document.querySelector('#checkout');
    if (el) el.click();
  });
  await delay(2000);

  console.log('\n=== CHECKOUT STEP 1 ===');
  console.log('URL:', page.url());
  const step1 = await page.evaluate(() => {
    const dt = [...document.querySelectorAll('[data-test]')].map(e => ({
      tag: e.tagName, dataTest: e.getAttribute('data-test'), id: e.id
    }));
    return dt;
  });
  console.log(JSON.stringify(step1, null, 2));

  await browser.close();
})();
