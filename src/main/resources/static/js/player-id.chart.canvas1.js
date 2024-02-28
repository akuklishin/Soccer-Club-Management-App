window.addEventListener('load', function () {
    let playerId = document.getElementById('canvas1').dataset.playerId;

    fetch('/api/players/' + playerId)
        .then(response => response.json())
        .then(playerData => {

            console.log('Fetched player data: ', playerData);

            let labels = ['Goals Scored', 'Assists', 'Yellow Cards', 'Red Cards'];
            let data = [
                playerData.totalGoals,
                playerData.totalAssists,
                playerData.totalYellows,
                playerData.totalReds
            ];

            let ctx = document.getElementById('canvas1').getContext('2d');
            let config = {
                type: 'polarArea',
                data: {
                    labels: labels,
                    datasets: [{
                        label: 'Stats Summary',
                        data: data,
                        backgroundColor: [
                            'rgba(75, 192, 192, 0.5)',
                            'rgba(54, 162, 235, 0.5)',
                            'rgba(255, 206, 86, 0.5)',
                            'rgba(255, 99, 132, 0.5)'
                        ],
                        borderColor: [
                            'rgba(75, 192, 192, 1)',
                            'rgba(54, 162, 235, 1)',
                            'rgba(255, 206, 86, 1)',
                            'rgba(255, 99, 132, 1)'
                        ],
                        borderWidth: 0
                    }]
                },
                options: {
                    responsive: true,
                    maintainAspectRatio: false,
                    plugins: {
                        legend: {
                            display: false,
                            position: 'bottom'
                        }
                    },
                    scales: {
                        r: {
                            grid: {
                                display: true,
                                color: 'rgba(153,0,255,0.25)'
                            },
                            ticks: {
                                beginAtZero: true,
                                stepSize: 1,
                                precision: 0,
                                callback: function(value) {
                                    if (Number.isInteger(value)) {
                                        return value;
                                    }
                                },
                                font: {
                                    size: 18
                                },
                                color: 'rgba(153,0,255,0.75)'
                            },
                            pointLabels: {
                                display: true,
                                centerPointLabels: true,
                                font: {
                                    size: 12
                                },
                                color: 'rgba(153,0,255,0.75)'
                            }
                        }
                    }
                },
            };

            let chart = new Chart(ctx, config);

        })
        .catch(error => console.error('Error fetching player data:', error));
});
