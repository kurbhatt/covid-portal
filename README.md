## Covid Dashboard
1. Application will be available on http://localhost:8080 or http://localhost:8080/login-page
2. H2 in-memory database has been used, so everytime whenever application starts registration is mandatory before login.
3. After Successful registration user will be auto logged in and redirected to dashboard page directly.
4. On dashboard user can see various covid-19 related stats for India.
5. Along with stats graphs has been used for depicting daily cases and daily RTPCR sample collected.
6. All data has been fetched from https://www.covid19india.org/. API details available on https://github.com/covid19india/api
7. Unit test case is available in `UserControllerUnitTest` File. 
8. Integration test case is available in `UserControllerIntegrationTest` File.
