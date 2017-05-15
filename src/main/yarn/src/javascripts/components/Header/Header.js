import React from 'react'
import PropTypes from 'prop-types'
import _ from 'underscore';
import { LinkContainer } from 'react-router-bootstrap';
import {
    Grid,
    Row,
    Col,
    Breadcrumb
} from 'react-bootstrap';

import { Colors } from 'consts';

import ROUTES_STRUCTURE, { findActiveNodes } from 'routes/structure';

import classes from './Header.scss';

const header = (title, path, containerFluid, children) => {
  return (
        <div className="sub-navbar sub-navbar__header-breadcrumbs">
            <Grid fluid={ containerFluid }>
                <Row>
                    <Col xs={ children ? 6 : 12 } className='sub-navbar-column'>
                        <div className="sub-navbar-header">
                            <h3>{ title || 'Home' }</h3>
                        </div>
                        {
                            (path && path.length > 0) ?
                                (
                                    <Breadcrumb
                                        className='navbar-text navbar-right no-bg'
                                    >
                                        <LinkContainer to='/' onlyActiveOnIndex>
                                            <Breadcrumb.Item active={ false }>
                                                <i className="fa fa-fw fa-home"></i>
                                            </Breadcrumb.Item>
                                        </LinkContainer>
                                        {
                                            _.map(path.reverse(), (pathItem, index) => (
                                                <Breadcrumb.Item
                                                    href='javascript:void(0)'
                                                    active={ index === path.length - 1 }
                                                    key={ `header-breadcrumb-${index}` }
                                                >{ pathItem.title }</Breadcrumb.Item>
                                            ))
                                        }
                                    </Breadcrumb>
                                ) : null
                        }

                    </Col>
                    {
                        children && (
                            <Col xs={ 6 }>
                                { children }
                            </Col>
                        )
                    }
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
