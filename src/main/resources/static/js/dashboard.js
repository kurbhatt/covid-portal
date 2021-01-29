var ctxTime = document.getElementById("covidTimeSeries").getContext('2d');
var ctxRTPCRTime = document.getElementById("rtpcrSeries").getContext('2d');
var casesTS = covidReport['timeSeries'];
var rtpcrTS = covidReport['rtpcrTests'];

var covidTimeSeries = new Chart(ctxTime, {
  type: 'bar',
  data: {
        datasets: [{
            label: '# of Cases',
            data: casesTS,
            borderWidth: 1
        }]
    },
  options: {
      scales: {
          yAxes: [{
              ticks: {
                  beginAtZero:true
              }
          }],
          xAxes: [{
              type: 'time',
              time: {
                  unit: 'month'
              }
          }]
      }
  }
});

var rtpcrTimeSeries = new Chart(ctxRTPCRTime, {
  type: 'bar',
  data: {
        datasets: [{
            label: '# of RTPCR Samples',
            data: rtpcrTS,
            borderWidth: 1
        }]
    },
  options: {
      scales: {
          yAxes: [{
              ticks: {
                  beginAtZero:true
              }
          }],
          xAxes: [{
              type: 'time',
              time: {
                parser: 'DD/MM/YYYY',
                unit: 'month',
                displayFormats: {
                 'day': 'DD/MM/YYYY'
                }
              }
          }]
      }
  }
});
