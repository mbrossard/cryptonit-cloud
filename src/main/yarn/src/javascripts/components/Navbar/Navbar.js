import React from 'react';
import PropTypes from 'prop-types'
import {
    Navbar as ReactBootstrapNavbar
} from 'react-bootstrap';

import classNames from 'classnames';

import classes from './Navbar.scss';

import updateChildElementOfType from './../utils/updateChildElementOfType';

const Navbar = props => {
    const { className, children, ...otherProps } = props;

    const navbarClass = classNames(className, classes.navbar);

    return (
        <ReactBootstrapNavbar className={ navbarClass } { ...otherProps }>
            { children }
        </ReactBootstrapNavbar>
    );
};

export default Navbar;
