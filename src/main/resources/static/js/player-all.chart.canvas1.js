window.addEventListener('load', function () {
    fetch('/api/players')
        .then(response => response.json())
        .then(playersData => {
            console.log('Fetched players data: ', playersData);

            let totalGoals = playersData.reduce((sum, playerData) => sum + playerData.totalGoals, 0);
            let totalAssists = playersData.reduce((sum, playerData) => sum + playerData.totalAssists, 0);
            let totalYellows = playersData.reduce((sum, playerData) => sum + playerData.totalYellows, 0);
            let totalReds = playersData.reduce((sum, playerData) => sum + playerData.totalReds, 0);

            let labels = ['Total Goals', 'Total Assists', 'Total Yellow Cards', 'Total Red Cards'];
            let data = [totalGoals, totalAssists, totalYellows, totalReds];

            let ctx = document.getElementById('canvas1').getContext('2d');
            let config = {
                type: 'bar',
                data: {
                    labels: labels,
                    datasets: [{
                        label: 'Player Stats',
                        data: data,
                        backgroundColor: [
                            'rgba(75, 192, 192, 0.5)',
                            'rgba(54, 162, 235, 0.5)',
                            'rgba(255, 206, 86, 0.5)',
                            'rgba(255, 99, 132, 0.5)',
                        ],
                        borderColor: [
                            'rgba(75, 192, 192, 1)',
                            'rgba(54, 162, 235, 1)',
                            'rgba(255, 206, 86, 1)',
                            'rgba(255, 99, 132, 1)',
                        ],
                        borderWidth: 1
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
                        y: {
                            grid: {
                                display: true,
                                color: 'rgba(153,0,255,0.25)'
                            },
                            ticks: {
                                beginAtZero: true,
                                stepSize: 5,
                                precision: 0,
                                callback: function(value) {
                                    if (Number.isInteger(value)) {
                                        return value;
                                    }
                                },
                                color: 'rgba(153,0,255,0.75)'
                            }
                        },
                        x: {
                            grid: {
                                display: false
                            },
                            ticks: {
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
        .catch(error => console.error('Error fetching players data:', error));
});
