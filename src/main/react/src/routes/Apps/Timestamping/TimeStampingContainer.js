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
                    <Col md={ 12 } className='m-b-3'>
                      <div className='flex-space-between'>
                        <h3 className="f-w-300 m-t-1">
                          <Link to='/timestamping'>
                            Timestamping
                          </Link>
                        </h3>
                        <div className='hidden-xs'>
                          <Button bsStyle='primary' className='m-l-1'>
                            <span className="hidden-md hidden-sm hidden-xs">
                              Refresh
                            </span>
                            <i className="fa fa-fw fa-refresh"></i>
                          </Button>
                          <Button bsStyle='primary' className='m-l-1'>
                            <span className="hidden-md hidden-sm hidden-xs">
                              Add Timestamping
                            </span>
                            <i className="fa fa-fw fa-plus"></i>
                          </Button>
                        </div>
                      </div>
                    </Col>
                </Row>
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
