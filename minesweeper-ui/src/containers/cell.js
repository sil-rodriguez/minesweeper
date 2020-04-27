import React, { Component } from "react";
import classNames from "classnames";

type Props = {
  row: number,
  column: number,
  value: string | number,
  onClickFunction: Function,
  onContextMenuFunction: Function,
  status: string,
  gameId: string
};

class Cell extends Component<Props, State> {
  handleClick({ target }: SyntheticMouseEvent<>) {
    let { onClickFunction } = this.props;
    onClickFunction();
  }
  handleContextMenu(e: SyntheticMouseEvent<>) {
    let { onContextMenuFunction } = this.props;
    e.preventDefault();
    onContextMenuFunction();
  }

  render() {
    let { row, column, value, status } = this.props;
    let cellsClass = classNames({
      cell: true,
      bomb: value === "☀",
      clicked: status === "VISIBLE"
    });
    return (
      <td
        id={`${row}_${column}`}
        className={cellsClass}
        onClick={this.handleClick.bind(this)}
        onContextMenu={this.handleContextMenu.bind(this)}
      >
        {status === "VISIBLE" ? value : ""}
        {status === "FLAGGED" ? "⚑" : ""}
      </td>
    );
  }
}


export default Cell;
