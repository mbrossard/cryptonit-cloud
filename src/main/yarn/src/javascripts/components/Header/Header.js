import React from 'react'
import PropTypes from 'prop-types'
import {
    Grid,
    Row,
    Col
} from 'react-bootstrap';

import { Colors } from 'consts';

import ROUTES_STRUCTURE, { findActiveNodes } from 'routes/structure';

import classes from './Header.scss';

const header = (title, path, containerFluid, children) => {
  return (
        <div className="sub-navbar sub-navbar__header">
            <Grid fluid={ containerFluid }>
                <Row>
                    <Col lg={ 12 }>
                        <div className={ classes.simpleHeaderWrap }>
                            <Row>
                                <Col xs={ children ? 6 : 12 }>
                                    <div className={ classes.headerPart }>
                                        <h1>
                                            { title }
                                        </h1>
                                    </div>
                                </Col>
                                {
                                    children && (
                                        <Col xs={ 6 }>
                                            <div className={ classes.headerPart }>
                                                { children }
                                            </div>
                                        </Col>
                                    )
                                }
                            </Row>
                        </div>
                    </Col>
                </Row>
            </Grid>
        </div>
    );
}

const Header = (props) => {
    const path = findActiveNodes(ROUTES_STRUCTURE, props.currentUrl);
    const { title } = (path && path.length > 0) ? path[0] : '';

    return header(title, path, props.fluid, props.children);
}

Header.propTypes = {
    currentUrl: PropTypes.string.isRequired,
    style: PropTypes.string.isRequired,
    fluid: PropTypes.bool.isRequired,
    children: PropTypes.node
}

Header.defaultProps = {
    children: null
}

export default Header;
