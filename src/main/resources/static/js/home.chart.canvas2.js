window.addEventListener('load', function () {
    fetch('/api/matches')
        .then(response => response.json())
        .then(matchesData => {

            console.log('Fetched matches: ', matchesData);

            let labels = matchesData.map(function (e) {
                return e.date;
            });

            console.log('Labels: ', labels);

            let accumulatedData = [];

            let accumulator = 0;
            matchesData.forEach(function (e) {
                accumulator += e.result.toLowerCase() === 'win' ? 1 : e.result.toLowerCase() === 'lose' ? -1 : 0;
                accumulatedData.push(accumulator);
            });

            console.log(accumulatedData);

            let ctx = canvas2.getContext('2d');
            let config = {
                type: 'line', data: {
                    labels: labels, datasets: [{
                        label: 'Soccer Match Results',
                        data: accumulatedData,
                        backgroundColor: 'rgba(102,0,204,0.3)',
                        borderColor: 'rgb(99,0,204)',
                        borderWidth: 1.5,
                        fill: false,
                        lineTension: 0.1
                    }]
                },
                options: {
                    responsive: true,
                    maintainAspectRatio: false,
                    plugins: {
                        legend: {
                            display: false
                        }
                    },
                    scales: {
                        x: {
                            ticks: {
                                display: true,
                                color: 'rgba(153,0,255,0.75)'
                            },
                            grid: {
                                display: true,
                                color: 'rgba(153,0,255,0.25)'
                            }
                        },
                        y: {
                            ticks: {
                                display: false
                            },
                            grid: {
                                display: true,
                                color: 'rgba(153,0,255,0.1)'
                            }
                        }
                    }
                }

            };

            let chart = new Chart(ctx, config);
        })
        .catch(error => console.error('Error fetching matches data:', error));
});
