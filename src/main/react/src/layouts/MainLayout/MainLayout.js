import React from 'react';
import PropTypes from 'prop-types'
import ROUTES, { findActiveNodes } from './../../routes/structure';


// Components
import {
    Grid,
    Layout,
    Navbar,
    Header,
} from 'components';

import classes from './MainLayout.scss';

import {
    toggleNavbarExpanded,
} from './modules/layout.js';

// Sub Components
import {
    LayoutOptions,
} from './components';


const sidebarAddOns = {
}

class MainLayout extends React.Component {
    static propTypes = {
    };

    constructor(props, context) {
        super(props, context);
    }

    render() {
        return (
            <Layout
                { ...this.props }
            >
                <Layout.Navigation>
                    <Navbar
                    >
                        <Navbar.Header>
                        </Navbar.Header>
                    </Navbar>
                </Layout.Navigation>
                {
                    this.props.rawContent ? (
                        <Layout.Content>
                            { this.props.children }
                        </Layout.Content>
                    ) : (
                        <Layout.Content>
                            <Header
                            />
                            <Grid>
                                { this.props.children }
                            </Grid>
                        </Layout.Content>
                    )
                }
            </Layout>
        )
    };
}

const mapStateToProps = (state) => ({
});

const mapActionCreators = {
};

export default connect(mapStateToProps, mapActionCreators)(MainLayout);
