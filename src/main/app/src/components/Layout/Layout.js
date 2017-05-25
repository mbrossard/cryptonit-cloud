import React from 'react';
import PropTypes from 'prop-types'
import _ from 'underscore';

class Layout extends React.Component {
    static propTypes = {
        children: PropTypes.node.isRequired
    };

    render() {
        const { children, className, ...otherProps } = this.props;

        return (
            <div
                id="application"
                className={ className }
            >
                <div className="main-wrap">
                    { children }
                </div>
            </div>
        )
    }
}

export default Layout;
