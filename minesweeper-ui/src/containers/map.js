// @flow

import React, { Component } from "react";
import Cell from "./cell";
import {populateMap} from "../helpers";

type Props = {};
type State = {
  mapSize: number,
  bombCount: number,
  theMap: Array<Array<number | string>>,
  cellsClicked: number,
  isLoaded: boolean,
  error: string
};

class Map extends Component<Props, State> {
  constructor(props: Props) {
    super(props);
    let mapSize = 10;
    let bombCount = 10;
    this.state = {
      mapSize,
      bombCount,
      cellsClicked: 1
    };
  }
"☀"
  componentDidMount() {
      fetch("http://localhost:8080/cells")
        .then(res => res.json())
        .then(
          (result) => {
            this.setState({
              isLoaded: true,
              theMap: populateMap(result)
            });
          },
          (error) => {
            this.setState({
              isLoaded: true,
              error
            });
          }
        )
      }

  incCellsClicked() {
    let { mapSize, bombCount, cellsClicked } = this.state;
    let safeCells = mapSize * mapSize - bombCount;
    this.setState({
      cellsClicked: cellsClicked + 1
    });
    if (cellsClicked >= safeCells) alert("☀☀☀ You have won! ☀☀☀");
  }

  render() {
    const { error, isLoaded} = this.state;
    if (error) {
      return <div>Error: {error.message}</div>;
    } else if (!isLoaded) {
      return <div>Loading...</div>;
    } else {
      return (
        <div>
          <table>
            <tbody>
              {this.state.theMap.map((item, row) => {
                return (
                  <tr key={row} className="mapRow">
                    {item.map((subitem, col) => {
                      return (
                        <Cell
                          key={col}
                          row={row}
                          column={col}
                          value={subitem}
                          incCellsClicked={this.incCellsClicked.bind(this)}
                        />
                      );
                    })}
                  </tr>
                );
              })}
            </tbody>
          </table>
        </div>
      );
    }
  }
}

export default Map;
