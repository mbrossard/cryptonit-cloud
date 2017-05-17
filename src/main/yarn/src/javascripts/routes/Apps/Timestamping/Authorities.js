import React, { Component } from 'react'

import {
    DropdownButton,
    MenuItem,
    Table
} from 'components';

const timestampingActionsDropdown = (index) => (
    <DropdownButton title='Action' id={`dropdown-basic-${index}`}>
        <MenuItem eventKey="1">
            <i className='fa fa-cog fa-fw text-gray-lighter m-r-1'></i>
            Configure
        </MenuItem>
        <MenuItem eventKey="2">
            <i className='fa fa-power-off fa-fw text-gray-lighter m-r-1'></i>
            Disable
        </MenuItem>
        <MenuItem eventKey="3">
            <i className='fa fa-remove fa-fw text-gray-lighter m-r-1'></i>
            Delete
        </MenuItem>
    </DropdownButton>
);

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
        <tr key={"authority_" + i}>
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
                  { timestampingActionsDropdown(authorities.id) }
              </td>
            </tr>
        )}
      </tbody>
      </Table>
    )
  }
};
