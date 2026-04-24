const output = document.querySelector("#output");
const apiStatus = document.querySelector("#apiStatus");

document.querySelectorAll(".tab").forEach((tab) => {
  tab.addEventListener("click", () => {
    document.querySelectorAll(".tab").forEach((item) => item.classList.remove("active"));
    document.querySelectorAll(".view").forEach((view) => view.classList.remove("active"));
    tab.classList.add("active");
    document.getElementById(tab.dataset.tab).classList.add("active");
  });
});

document.querySelector("#clearLog").addEventListener("click", () => {
  output.textContent = "Ready.";
});

document.querySelectorAll(".api-form").forEach((form) => {
  form.addEventListener("submit", async (event) => {
    event.preventDefault();
    await submitForm(form);
  });
});

async function submitForm(form) {
  const method = form.dataset.method || "GET";
  const queryFields = new Set((form.dataset.queryFields || "").split(",").map((item) => item.trim()).filter(Boolean));
  const values = readForm(form);
  const query = new URLSearchParams();
  let body;

  if (method === "GET" || form.dataset.json !== "true") {
    Object.entries(values).forEach(([key, value]) => {
      if (value !== "" && value !== undefined) {
        query.set(key, value);
      }
    });
  } else {
    const json = {};
    Object.entries(values).forEach(([key, value]) => {
      if (queryFields.has(key)) {
        query.set(key, value);
      } else {
        setNested(json, key, value);
      }
    });
    body = JSON.stringify(json);
  }

  const url = `${form.dataset.endpoint}${query.toString() ? `?${query}` : ""}`;

  writeLog(`${method} ${url}\nSending...`);

  try {
    const response = await fetch(url, {
      method,
      headers: body ? { "Content-Type": "application/json" } : {},
      body
    });
    const text = await response.text();
    const data = tryJson(text);
    writeLog(`${method} ${url}\nStatus: ${response.status}\n\n${format(data ?? text)}`);
  } catch (error) {
    writeLog(`${method} ${url}\nRequest failed: ${error.message}`);
  }
}

function readForm(form) {
  const formData = new FormData(form);
  const values = {};
  for (const [key, value] of formData.entries()) {
    values[key] = normalizeValue(value);
  }
  return values;
}

function normalizeValue(value) {
  const trimmed = String(value).trim();
  if (trimmed === "") {
    return "";
  }
  if (/^-?\d+(\.\d+)?$/.test(trimmed)) {
    return Number(trimmed);
  }
  return trimmed;
}

function setNested(target, path, value) {
  if (value === "") {
    return;
  }

  const parts = path.split(".");
  let current = target;
  parts.forEach((part, index) => {
    if (index === parts.length - 1) {
      current[part] = value;
      return;
    }
    current[part] = current[part] || {};
    current = current[part];
  });
}

function tryJson(text) {
  try {
    return JSON.parse(text);
  } catch {
    return null;
  }
}

function format(value) {
  return typeof value === "string" ? value : JSON.stringify(value, null, 2);
}

function writeLog(message) {
  output.textContent = message;
}

async function checkApi() {
  try {
    const response = await fetch("/customer/find?mobno=0");
    apiStatus.textContent = response.status === 404 ? "API ready" : "API online";
    apiStatus.classList.add("ok");
  } catch {
    apiStatus.textContent = "API offline";
    apiStatus.classList.add("bad");
  }
}

checkApi();
