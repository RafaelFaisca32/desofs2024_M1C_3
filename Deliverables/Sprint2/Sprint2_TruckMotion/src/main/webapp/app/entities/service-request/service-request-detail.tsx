import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './service-request.reducer';
import { AUTHORITIES } from '../../config/constants';

export const ServiceRequestDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  const account = useAppSelector(state => state.authentication.account);
  const customerRole = [AUTHORITIES.CUSTOMER];
  const authorities = account.authorities as string[]

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const serviceRequestEntity = useAppSelector(state => state.serviceRequest.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="serviceRequestDetailsHeading">Service Request</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="items">Items</span>
          </dt>
          <dd>{serviceRequestEntity.items}</dd>
          <dt>
            <span id="serviceName">Service Name</span>
          </dt>
          <dd>{serviceRequestEntity.serviceName}</dd>
          <dt>
            <span id="totalWeightOfItems">Total Weight Of Items</span>
          </dt>
          <dd>{serviceRequestEntity.totalWeightOfItems}</dd>
          <dt>
            <span id="price">Price</span>
          </dt>
          <dd>{serviceRequestEntity.price}</dd>
          <dt>
            <span id="date">Date</span>
          </dt>
          <dd>
            {serviceRequestEntity.date ? <TextFormat value={serviceRequestEntity.date} type="date" format={APP_DATE_FORMAT} /> : null}
          </dd>
          <dt>Location Coordinates(x,y,z)</dt>
          <dd>{serviceRequestEntity.location ?  serviceRequestEntity.location.coordX +"--" +serviceRequestEntity.location.coordY+"--" +serviceRequestEntity.location.coordZ : ''}</dd>
          <dt>Customer Company</dt>
          <dd>{serviceRequestEntity.customer ? serviceRequestEntity.customer.company : ''}</dd>
          <dt>Status</dt>
          <dd>{serviceRequestEntity.status ? serviceRequestEntity.status.status : ''}</dd>
        </dl>
        <Button tag={Link} to="/service-request" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;

        {customerRole.some(value => authorities.includes(value)) && serviceRequestEntity?.status === 'PENDING' ?
        <Button tag={Link} to={`/service-request/${serviceRequestEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
        : null}
      </Col>
    </Row>
  );
};

export default ServiceRequestDetail;
