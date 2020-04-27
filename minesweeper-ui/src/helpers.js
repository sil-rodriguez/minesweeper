// @flow
export function clickCell(mapComponent, gameId: string, row: int, column: int, action: string) {
  const requestOptions = {
          method: 'POST',
          headers: { 'Content-Type': 'application/json' },
          body: JSON.stringify({gameId: gameId, cell: {row: row, column: column}, action: action})
      };
  fetch("http://localhost:8080/cells", requestOptions)
    .then(res => res.json())
    .then(
      (result) => {
        if(result.message !== undefined && result.board !== null){
          alert(result.message);
        }else{
            mapComponent.setState({
              isLoaded: true,
              theMap: populateMap(result.board),
              gameId: result.gameId
            });
            if(result.won) alert("☀☀☀ You have won! ☀☀☀");
            if(result.over) endGame(row,column);
          }
      },
      (error) => {
        mapComponent.setState({
          isLoaded: true,
          error
        });
      }
    )
}

export function populateMap(board: Array<Array<object>>) {
  let outerArray: Array<Array<object>> = [];
  for (let i = 0; i < board.length; i++) {
    let innerArray: Array<object> = [];
    let row: Array<number | string> = board[i];
    for (let j = 0; j < row.length; j++) {
      let cell = row[j];
      let cellObj = Object.create({});
      if(cell.bomb){
        cellObj.value = "☀";
      }else{
        cellObj.value = cell.adjacentBombs;
      }
      cellObj.status = cell.status;
      innerArray.push(cellObj);
    }
    outerArray.push(innerArray);
  }
  return outerArray;
}

function endGame(row: int, col:int ) {
  let target = document.getElementById(`${row}_${col}`);
  target.style.backgroundColor = "black";
  alert("Game over");
  return;
}
