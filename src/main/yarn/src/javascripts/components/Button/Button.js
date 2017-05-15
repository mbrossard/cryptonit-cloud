import React from 'react';
import PropTypes from 'prop-types'
import {
    Button as BootstrapButton
} from 'react-bootstrap';
import _ from 'underscore';
import classNames from 'classnames';

import classes from './Button.scss';

import { Colors } from 'consts';

const BS_STYLES = [
    'default',
    'link',
    'primary',
    'success',
    'warning',
    'danger',
    'info'
];

class Button extends React.Component {
    static propTypes = {
        children: PropTypes.node.isRequired,
        outline: PropTypes.bool,
        bsStyle: PropTypes.string,
        customColor: PropTypes.string
    }

    static defaultProps = {
        outline: false,
        bsStyle: 'default'
    }

    render() {
        const {
            className,
            children,
            bsStyle,
            outline,
            customColor,
            ...otherProps
        } = this.props;

        const isBsStyle = _.contains(BS_STYLES, bsStyle);

        const buttonClass = classNames({
            [`${classes.outline}`]: outline && !isBsStyle,
            'btn-outline': outline && isBsStyle
        }, className);

        const additionalStyle = {
            color: outline ? customColor : '#fff',
            backgroundColor: customColor,
            borderColor: customColor
        }

        return (
            <BootstrapButton
                { ...otherProps }
                bsStyle={ isBsStyle ? bsStyle : null }
                className={ buttonClass }
                style={ !isBsStyle ? additionalStyle : null }
            >
                { children }
            </BootstrapButton>
        );
    }
}

export default Button;
