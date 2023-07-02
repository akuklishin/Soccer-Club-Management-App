window.addEventListener('load', function () {
    fetch('/api/matches')
        .then(response => response.json())
        .then(matchesData => {

            console.log('Fetched matches: ', matchesData);

            let labels = ['Win', 'Draw', 'Lose'];
            let winCount = 0;
            let drawCount = 0;
            let loseCount = 0;

            matchesData.forEach(match => {
                if (match.result === 'WIN') {
                    winCount++;
                } else if (match.result === 'DRAW') {
                    drawCount++;
                } else if (match.result === 'LOSE') {
                    loseCount++;
                }
            });

            console.log('Processed matches: ', { winCount, drawCount, loseCount });

            let data = [winCount, drawCount, loseCount];

            let ctx = document.getElementById('canvas1').getContext('2d');
            let config = {
                type: 'doughnut',
                data: {
                    labels: labels,
                    datasets: [{
                        label: 'Soccer Match Results',
                        data: data,
                        backgroundColor: [
                            'rgba(75,192,192,0.5)',
                            'rgba(255,206,86,0.5)',
                            'rgba(255,99,132,0.5)'
                        ],
                        borderColor: [
                            'rgba(75, 192, 192, 1)',
                            'rgba(255, 206, 86, 1)',
                            'rgba(255, 99, 132, 1)'
                        ],
                        borderWidth: 2
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
                    circumference: 180,
                    rotation: 270
                },
            };

            let chart = new Chart(ctx, config);
        })
        .catch(error => console.error('Error fetching matches data:', error));
});
