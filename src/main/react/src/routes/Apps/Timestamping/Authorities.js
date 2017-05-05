import React, { Component } from 'react'

import {
    Table
} from 'components';

export default class Authorities extends Component {
  constructor(props) {
    super(props);

    this.state = {
      authorities: []
    };
  }

  componentDidMount() {
  }

  render() {
    return (
    <Table>
      <thead>
        <tr>
          <th>id</th>
          <th>domain</th>
          <th>subject</th>
          <th>policy</th>
          <th>actions</th>
        </tr>
      </thead>
      <tbody>
        {this.state.authorities.map((authorities, i) =>
        <tr>
            <td>
                <span>
                  {authorities.id}
                </span>
              </td>
              <td>
                <span>
                  {authorities.domain}
                </span>
              </td>
              <td>
                <span className='text-white'>
                  {authorities.subject}
                </span>
              </td>
              <td>
                <span>
                  {authorities.policy}
                </span>
              </td>
              <td>
              </td>
            </tr>
        )}
      </tbody>
      </Table>
    )
  }
};
