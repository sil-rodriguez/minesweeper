// @flow

import React, { Component } from "react";
import Cell from "./cell";
import {populateMap, clickCell} from "../helpers";

type Props = {};
type State = {
  mapSize: number,
  bombCount: number,
  theMap: Array<Array<object>>,
  isLoaded: boolean,
  error: string,
  gameId: string
};

class Map extends Component<Props, State> {
  constructor(props: Props) {
    super(props);
    let mapSize = 10;
    let bombCount = 10;
    this.state = {
      mapSize,
      bombCount
    };
  }

  componentDidMount() {
      fetch("http://localhost:8080/cells")
        .then(res => res.json())
        .then(
          (result) => {
            this.setState({
              isLoaded: true,
              theMap: populateMap(result.board),
              gameId: result.gameId
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
                          value={subitem.value}
                          status={subitem.status}
                          onClickFunction={() => clickCell(this, this.state.gameId, row, col, "CLICK")}
                          onContextMenuFunction={() => clickCell(this, this.state.gameId, row, col, "FLAG")}
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
