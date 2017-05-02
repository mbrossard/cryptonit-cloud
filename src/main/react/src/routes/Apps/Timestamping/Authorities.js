import React, { Component } from 'react'
import PropTypes from 'prop-types'

export default class Authorities extends Component {
  render() {
    return (
    <Table>
      <thead>
        <tr>
          <th></th>
          <th></th>
          <th></th>
          <th></th>
          <th></th>
        </tr>
      </thead>
      <tbody>
        {this.props.authorities.map((tsa, i) =>
        )}
      </tbody>
      </Table>
    )
  }
}

Authorities.propTypes = {
  authorities: PropTypes.array.isRequired
}
