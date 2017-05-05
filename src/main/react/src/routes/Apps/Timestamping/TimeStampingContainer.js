import React from 'react';

import {
    Row,
    Col,
} from 'components';

import { RoutedComponent, connect } from 'routes/routedComponent';
import { CONTENT_VIEW_STATIC } from 'layouts/MainLayout/modules/layout';

import Authorities from 'routes/Apps/timestamping/Authorities'

class TimeStampingContainer extends RoutedComponent {
    getLayoutOptions() {
        return {
            contentView: CONTENT_VIEW_STATIC
        }
    }

    render() {
        const { section: sectionName } = this.props.routeParams;
        return (
            <div>
                <Row>
                    <Col lg={ 12 }>
                      <Authorities/>
                    </Col>
                </Row>
            </div>
        );
    }
}

export default connect()(TimeStampingContainer);
