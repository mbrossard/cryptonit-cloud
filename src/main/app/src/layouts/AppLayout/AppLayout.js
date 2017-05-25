import React from 'react';

import {
    Grid,
    Header,
    Layout,
    Nav,
    Navbar,
    NavDropdown,
    NavItem,
    MenuItem
} from '../../components';

class AppLayout extends React.Component {
    static propTypes = {
    };

    render() {
        return (
          <Layout { ...this.props }>
            <Layout.Navigation>
              <Navbar>
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
                    <Header />
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

export default AppLayout;
