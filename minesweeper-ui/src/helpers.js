// @flow

export function populateMap(result) {
  let outerArray: Array<Array<number | string>> = [];
  let board: Array<Array<>> = result.board;
  for (let i = 0; i < board.length; i++) {
    let innerArray: Array<number | string> = [];
    let row: Array<number | string> = board[i];
    for (let j = 0; j < row.length; j++) {      
      let cell = row[j];
      if(cell.bomb){
        innerArray.push("☀");
      }else{
        innerArray.push(cell.adjacentBombs);
      }
    }
    outerArray.push(innerArray);
  }
  return outerArray;
}
