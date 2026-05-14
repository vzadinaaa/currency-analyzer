import { useState, useEffect } from "react";

function App() {

  const [base, setBase] = useState("EUR");

  const [symbols, setSymbols] =
    useState("USD,CZK,GBP");

  const popularCurrencies = [

    "USD",
    "EUR",
    "CZK",
    "GBP",
    "JPY",
    "AUD",
    "CAD",
    "CHF",
    "CNY",
    "RUB"
  ];

  const today = new Date();

  const defaultDate =
    today.toISOString().split("T")[0];

  const [selectedDate, setSelectedDate] =
    useState(defaultDate);

  const [analysis, setAnalysis] =
    useState(null);

  const [ratesTable, setRatesTable] =
    useState([]);

  const [loggedIn, setLoggedIn] =
    useState(false);

  const [username, setUsername] =
    useState("");

  const [password, setPassword] =
    useState("");

  const [loginError, setLoginError] =
    useState("");

  const [loading, setLoading] =
    useState(false);

  const [loginLoading, setLoginLoading] =
    useState(false);

  const [settingsSaved, setSettingsSaved] =
    useState(false);

  const [error, setError] =
    useState("");

  const [language, setLanguage] =
    useState("en");

  const translations = {

    en: {

      title: "Currency Analyzer",

      configuration: "Configuration",

      baseCurrency: "Base Currency",

      selectedCurrencies:
        "Selected Currencies",

      selectedDate:
        "Selected Date",

      loadCurrent:
        "Load Current Rates",

      loadHistorical:
        "Load Historical Rates",

      saveSettings:
        "Save Settings",

      settingsSaved:
        "Settings saved successfully.",

      strongest:
        "Strongest",

      weakest:
        "Weakest",

      average:
        "Average",

      exchangeRates:
        "Exchange Rates",

      currency:
        "Currency",

      rate:
        "Rate",

      recommendedCurrencies:
        "Recommended currencies",

      currencyHint:
        "You can also manually add any currency using its code (example: SEK, NOK, INR).",

      loadingExchangeData:
        "Loading exchange data...",

      login:
        "Login",

      username:
        "Username",

      password:
        "Password",

      logout:
        "Logout",

      loading:
        "Loading...",

      invalidLogin:
        "Invalid username or password",

      backendUnavailable:
        "Backend server is unavailable",

      currentRatesError:
        "Current rates API error",

      historicalRatesError:
        "Historical rates API error",

      analysisError:
        "Analysis API error",
    },

    cs: {

      title:
        "Analyzátor Měn",

      configuration:
        "Nastavení",

      baseCurrency:
        "Základní měna",

      selectedCurrencies:
        "Vybrané měny",

      selectedDate:
        "Vybrané datum",

      loadCurrent:
        "Načíst aktuální kurzy",

      loadHistorical:
        "Načíst historické kurzy",

      saveSettings:
        "Uložit nastavení",

      settingsSaved:
        "Nastavení bylo úspěšně uloženo.",

      strongest:
        "Nejsilnější",

      weakest:
        "Nejslabší",

      average:
        "Průměr",

      exchangeRates:
        "Kurzy měn",

      currency:
        "Měna",

      rate:
        "Kurz",

      recommendedCurrencies:
        "Doporučené měny",

      currencyHint:
        "Libovolnou další měnu můžeš přidat ručně pomocí její zkratky (např. SEK, NOK, INR).",

      loadingExchangeData:
        "Načítání dat...",

      login:
        "Přihlášení",

      username:
        "Uživatelské jméno",

      password:
        "Heslo",

      logout:
        "Odhlásit se",

      loading:
        "Načítání...",

      invalidLogin:
        "Neplatné uživatelské jméno nebo heslo",

      backendUnavailable:
        "Backend server není dostupný",

      currentRatesError:
        "Chyba při načítání aktuálních kurzů",

      historicalRatesError:
        "Chyba při načítání historických kurzů",

      analysisError:
        "Chyba při analýze dat",
    }
  };

  const t = translations[language];

  useEffect(() => {

  const token =
    localStorage.getItem(
      "token"
    );

  if (!token) {

    return;
  }

  fetch(
    "https://currency-analyzer-aa0c.onrender.com/api/settings",
    {
      headers: {
        Authorization: token
      }
    }
  )

  .then(response =>
    response.json()
  )

  .then(data => {

    if (data) {

      setBase(data.baseCurrency);

      setSymbols(
        data.selectedCurrencies
      );
    }
  });

}, []);

  const toggleCurrency = (
    currency
  ) => {

    const current =
      symbols
        .split(",")
        .map(c => c.trim())
        .filter(c => c !== "");

    if (
      current.includes(currency)
    ) {

      const updated =
        current.filter(
          c => c !== currency
        );

      setSymbols(
        updated.join(",")
      );

    } else {

      current.push(currency);

      setSymbols(
        current.join(",")
      );
    }
  };

  const processAnalysisData = (data) => {

    const generatedRates =
      Object.entries(data.rates)
        .map(([currency, value]) => {

          return {
            currency: currency,
            value: value
          };
        });

    setRatesTable(
      generatedRates
    );

    setAnalysis(data);
  };

  const loadCurrentRates = () => {

    if (loading) {

      return;
    }

    setLoading(true);

    setError("");

    fetch(
      `https://currency-analyzer-aa0c.onrender.com/api/analysis?base=${base}&symbols=${symbols}`,
      {
        headers: {
          Authorization:
            localStorage.getItem(
              "token"
            )
        }
      }
    )

      .then(response => {

        if (!response.ok) {

          throw new Error(
            t.currentRatesError
          );
        }

        return response.json();
      })

      .then(data => {

        processAnalysisData(data);

        setLoading(false);
      })

      .catch((error) => {

        console.error(error);

        setError(error.message);

        setLoading(false);
      });
  };

  const loadHistoricalRates = () => {

    if (loading) {

      return;
    }

    setLoading(true);

    setError("");

    fetch(
      `https://currency-analyzer-aa0c.onrender.com/api/analysis/historical?base=${base}&symbols=${symbols}&date=${selectedDate}`,
      {
        headers: {
          Authorization:
            localStorage.getItem(
              "token"
            )
        }
      }
    )

      .then(response => {

        if (!response.ok) {

          throw new Error(
            t.historicalRatesError
          );
        }

        return response.json();
      })

      .then(data => {

        processAnalysisData(data);

        setLoading(false);
      })

      .catch((error) => {

        console.error(error);

        setError(error.message);

        setLoading(false);
      });
  };

  const login = () => {

    setLoginLoading(true);

    fetch(
      "https://currency-analyzer-aa0c.onrender.com/api/login",
      {

        method: "POST",

        headers: {
          "Content-Type":
            "application/json"
        },

        body: JSON.stringify({
          username: username,
          password: password
        })
      }
    )

      .then(response =>
        response.json()
      )

      .then(data => {

        setLoginLoading(false);

        if (data.success) {

          setLoggedIn(true);

          setLoginError("");

          localStorage.setItem(
            "token",
            data.token
          );

        } else {

          setLoginError(
            t.invalidLogin
          );
        }
      })

      .catch(() => {

        setLoginLoading(false);

        setLoginError(
          t.backendUnavailable
        );
      });
  };

  if (!loggedIn) {

    return (

      <div
        style={{
          minHeight: "100vh",
          backgroundColor: "#020817",
          display: "flex",
          justifyContent: "center",
          alignItems: "center",
          color: "white",
          fontFamily: "Arial"
        }}
      >

        <div
          style={{
            backgroundColor: "#0f172a",
            padding: "40px",
            borderRadius: "20px",
            width: "400px"
          }}
        >

          <h1
            style={{
              textAlign: "center",
              marginBottom: "30px"
            }}
          >
            {t.title}
          </h1>

          <div
            style={{
              marginBottom: "25px",
              display: "flex",
              justifyContent: "center",
              gap: "10px"
            }}
          >


            <button
              onClick={() =>
                setLanguage("en")
              }

              style={{
                padding: "8px 16px",
                borderRadius: "10px",
                border: "none",
                backgroundColor:

                  language === "en"
                    ? "#2563eb"
                    : "#1e293b",

                color: "white",

                cursor: "pointer"
              }}
            >
              EN
            </button>

            <button
              onClick={() =>
                setLanguage("cs")
              }

              style={{
                padding: "8px 16px",
                borderRadius: "10px",
                border: "none",
                backgroundColor:

                  language === "cs"
                    ? "#2563eb"
                    : "#1e293b",

                color: "white",

                cursor: "pointer"
              }}
            >
              CZ
            </button>

          </div>

          <div
            style={{
              marginBottom: "20px"
            }}
          >

            <label>
              {t.username}
            </label>

            <br />

            <input
              style={{
                marginTop: "10px",
                width: "100%",
                padding: "12px",
                borderRadius: "10px",
                border: "none",
                backgroundColor: "#1e293b",
                color: "white"
              }}
              value={username}
              onChange={(e) =>
                setUsername(e.target.value)
              }
            />

          </div>

          <div
            style={{
              marginBottom: "20px"
            }}
          >

            <label>
              {t.password}
            </label>

            <br />

            <input
              type="password"
              style={{
                marginTop: "10px",
                width: "100%",
                padding: "12px",
                borderRadius: "10px",
                border: "none",
                backgroundColor: "#1e293b",
                color: "white"
              }}
              value={password}
              onChange={(e) =>
                setPassword(e.target.value)
              }
            />

          </div>

          {loginError && (

            <p
              style={{
                color: "#ef4444"
              }}
            >
              {loginError}
            </p>
          )}

          <button
            onClick={login}

            disabled={loginLoading}

            style={{

              width: "100%",

              padding: "14px",

              borderRadius: "10px",

              border: "none",

              backgroundColor: "#2563eb",

              color: "white",

              fontSize: "16px",

              cursor:
                loginLoading
                  ? "not-allowed"
                  : "pointer",

              opacity:
                loginLoading
                  ? 0.7
                  : 1
            }}
          >
            {loginLoading
              ? t.loading
              : t.login}
          </button>

        </div>

      </div>
    );
  }

  return (

    <div
      style={{
        minHeight: "100vh",
        backgroundColor: "#020817",
        color: "white",
        fontFamily: "Arial",
        padding: "40px"
      }}
    >

      <div
        style={{
          maxWidth: "1000px",
          margin: "0 auto"
        }}
      >

        <h1
          style={{
            fontSize: "60px",
            marginBottom: "40px",
            textAlign: "center"
          }}
        >
          {t.title}
        </h1>



        <div
          style={{
            backgroundColor: "#0f172a",
            padding: "30px",
            borderRadius: "20px",
            marginBottom: "30px"
          }}
        >



          <div
            style={{
              marginBottom: "20px"
            }}
          >



            <button
              onClick={() =>
                setLanguage("en")
              }

              style={{
                padding: "8px 16px",
                borderRadius: "10px",
                border: "none",

                backgroundColor:

                  language === "en"
                    ? "#2563eb"
                    : "#1e293b",

                color: "white",

                cursor: "pointer"
              }}
            >
              EN
            </button>

            <button
              onClick={() =>
                setLanguage("cs")
              }

              style={{
                padding: "8px 16px",
                borderRadius: "10px",
                border: "none",

                backgroundColor:

                  language === "cs"
                    ? "#2563eb"
                    : "#1e293b",

                color: "white",

                cursor: "pointer"
              }}
            >
              CZ
            </button>



          </div>

          <button
            onClick={() => {

              setLoggedIn(false);

              setUsername("");

              setPassword("");
            }}

            style={{
              padding: "8px 16px",
              borderRadius: "10px",
              border: "none",

              backgroundColor: "#dc2626",

              color: "white",

              cursor: "pointer",

              marginLeft: "10px",

              marginBottom: "20px"
            }}
          >
            {t.logout}
          </button>

          <h2>
            {t.configuration}
          </h2>

          <div
            style={{
              marginBottom: "20px"
            }}
          >

            <label>
              {t.baseCurrency}
            </label>

            <br />

            <input
              style={{
                marginTop: "10px",
                padding: "10px",
                width: "250px",
                borderRadius: "10px",
                border: "none",
                backgroundColor: "#1e293b",
                color: "white"
              }}
              value={base}
              onChange={(e) =>
                setBase(
                  e.target.value.toUpperCase()
                )
              }
            />

          </div>

          <div
            style={{
              marginBottom: "35px",
              textAlign: "center"
            }}
          >

            <label
              style={{
                fontWeight: "bold",
                fontSize: "18px"
              }}
            >
              {t.selectedCurrencies}
            </label>

            <br />

            <input
              style={{
                marginTop: "20px",
                padding: "12px",
                width: "450px",
                maxWidth: "100%",
                borderRadius: "12px",
                border: "none",
                backgroundColor: "#1e293b",
                color: "white",
                textAlign: "center",
                fontSize: "15px"
              }}
              value={symbols}
              onChange={(e) =>
                setSymbols(
                  e.target.value.toUpperCase()
                )
              }
            />

            <div
              style={{
                width: "100%",
                height: "1px",
                backgroundColor: "#1e293b",
                marginTop: "35px",
                marginBottom: "30px"
              }}
            />

            <p
              style={{
                color: "#3b82f6",
                fontSize: "18px",
                fontWeight: "bold",
                marginBottom: "12px"
              }}
            >
              {t.recommendedCurrencies}
            </p>

            <p
              style={{
                color: "#64748b",
                marginBottom: "25px",
                fontSize: "14px"
              }}
            >
              {t.currencyHint}

            </p>

            <div
              style={{
                display: "flex",
                justifyContent: "center",
                flexWrap: "wrap",
                gap: "12px"
              }}
            >

              {popularCurrencies.map(
                (currency) => (

                  <button
                    key={currency}

                    onClick={() =>
                      toggleCurrency(currency)
                    }

                    style={{
                      padding: "10px 18px",
                      borderRadius: "12px",
                      border: "none",
                      cursor: "pointer",

                      backgroundColor:

                        symbols.includes(currency)
                          ? "#2563eb"
                          : "#1e293b",

                      color: "white",

                      fontWeight: "bold",

                      minWidth: "72px",

                      transition: "0.2s"
                    }}
                  >
                    {currency}
                  </button>
                )
              )}

            </div>

          </div>

          <div
            style={{
              marginBottom: "20px"
            }}
          >

            <label>
              {t.selectedDate}
            </label>

            <br />

            <input
              type="date"
              value={selectedDate}
              onChange={(e) =>
                setSelectedDate(
                  e.target.value
                )
              }
              style={{
                marginTop: "10px",
                padding: "10px",
                borderRadius: "10px",
                border: "none",
                backgroundColor: "#1e293b",
                color: "white"
              }}
            />

          </div>

          <button
            onClick={loadCurrentRates}

            disabled={loading}
            disabled={loading}
            style={{
              padding: "12px 25px",
              borderRadius: "12px",
              border: "none",
              backgroundColor: "#2563eb",
              color: "white",
              cursor: "pointer",
              fontSize: "16px"
            }}
          >
            {loading
              ? "Loading..."
              : t.loadCurrent}
          </button>

          <button
            onClick={loadHistoricalRates}
            disabled={loading}
            style={{
              padding: "12px 25px",
              borderRadius: "12px",
              border: "none",
              backgroundColor: "#dc2626",
              color: "white",
              cursor: "pointer",
              fontSize: "16px",
              marginLeft: "15px",
              opacity:
                loading ? 0.7 : 1,

              cursor:
                loading
                  ? "not-allowed"
                  : "pointer"
            }}
          >
            {loading
              ? "Loading..."
              : t.loadHistorical}
          </button>

          <button
            onClick={() => {

              fetch(
                "https://currency-analyzer-aa0c.onrender.com/api/settings",
                {

                  method: "POST",

                  headers: {
                    "Content-Type":
                      "application/json",

                    Authorization:
                      localStorage.getItem(
                        "token"
                      )
                  },

                  body: JSON.stringify({
                    baseCurrency: base,
                    selectedCurrencies: symbols,
                    language: language
                  })
                }
              )

                .then(() => {

                  setSettingsSaved(true);

                  setTimeout(() => {

                    setSettingsSaved(false);

                  }, 3000);
                });



            }}
            style={{
              padding: "12px 25px",
              borderRadius: "12px",
              border: "none",
              backgroundColor: "#16a34a",
              color: "white",
              cursor: "pointer",
              fontSize: "16px",
              marginLeft: "15px",
              opacity:
                loading ? 0.7 : 1,

              cursor:
                loading
                  ? "not-allowed"
                  : "pointer"
            }}
          >
            {t.saveSettings}
          </button>

          {settingsSaved && (

            <p
              style={{
                color: "#22c55e",
                marginTop: "15px",
                fontWeight: "bold"
              }}
            >
              {t.settingsSaved}
            </p>
          )}

        </div>

        {loading && (

          <p
            style={{
              marginBottom: "20px",
              color: "#38bdf8"
            }}
          >
            {t.loadingExchangeData}
          </p>
        )}

        {analysis?.fromCache && (

  <div
    style={{
      backgroundColor: "#f59e0b",
      color: "black",
      padding: "15px",
      borderRadius: "12px",
      marginBottom: "20px",
      fontWeight: "bold",
      textAlign: "center"
    }}
  >

    API unavailable.
    Using cached data from:

    {" "}

    {new Date(
  analysis.cacheDate
).toLocaleString()}

  </div>
)}

        {error && (

          <p
            style={{
              marginBottom: "20px",
              color: "#ef4444"
            }}
          >
            {error}
          </p>
        )}

        {analysis && (

          <>
            <div
              style={{
                display: "grid",
                gridTemplateColumns:
                  "1fr 1fr 1fr",
                gap: "20px",
                marginBottom: "40px"
              }}
            >

              <div
                style={{
                  backgroundColor: "#0f172a",
                  padding: "25px",
                  borderRadius: "20px"
                }}
              >
                <h3>
                  {t.strongest}
                </h3>

                <h2>
                  {analysis.strongestCurrency}
                </h2>

                <p>
                  {analysis.strongestValue}
                </p>
              </div>

              <div
                style={{
                  backgroundColor: "#0f172a",
                  padding: "25px",
                  borderRadius: "20px"
                }}
              >
                <h3>
                  {t.weakest}
                </h3>

                <h2>
                  {analysis.weakestCurrency}
                </h2>

                <p>
                  {analysis.weakestValue}
                </p>
              </div>

              <div
                style={{
                  backgroundColor: "#0f172a",
                  padding: "25px",
                  borderRadius: "20px"
                }}
              >
                <h3>
                  {t.average}
                </h3>

                <h2>
                  {
                    analysis?.average
                      ? analysis.average.toFixed(2)
                      : "N/A"
                  }
                </h2>
              </div>

            </div>

            <div
              style={{
                backgroundColor: "#0f172a",
                padding: "30px",
                borderRadius: "20px"
              }}
            >

              <h2
                style={{
                  marginBottom: "20px"
                }}
              >
                {t.exchangeRates}
              </h2>

              <table
                style={{
                  width: "100%",
                  borderCollapse: "collapse",
                  color: "white"
                }}
              >

                <thead>

                  <tr
                    style={{
                      backgroundColor:
                        "#1e293b"
                    }}
                  >

                    <th
                      style={{
                        padding: "15px",
                        textAlign: "left"
                      }}
                    >
                      {t.currency}
                    </th>

                    <th
                      style={{
                        padding: "15px",
                        textAlign: "left"
                      }}
                    >
                      {t.rate}
                    </th>

                  </tr>

                </thead>

                <tbody>

                  {ratesTable.map(
                    (rate, index) => (

                      <tr key={index}>

                        <td
                          style={{
                            padding: "15px",
                            borderBottom:
                              "1px solid #334155"
                          }}
                        >
                          {rate.currency}
                        </td>

                        <td
                          style={{
                            padding: "15px",
                            borderBottom:
                              "1px solid #334155"
                          }}
                        >
                          {rate.value}
                        </td>

                      </tr>
                    )
                  )}

                </tbody>

              </table>

            </div>

          </>
        )}

      </div>

    </div>
  );
}

export default App;