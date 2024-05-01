import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './service.reducer';

export const ServiceDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const serviceEntity = useAppSelector(state => state.service.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="serviceDetailsHeading">Service</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">Id</span>
          </dt>
          <dd>{serviceEntity.id}</dd>
          <dt>
            <span id="items">Items</span>
          </dt>
          <dd>{serviceEntity.items}</dd>
          <dt>
            <span id="serviceName">Service Name</span>
          </dt>
          <dd>{serviceEntity.serviceName}</dd>
          <dt>
            <span id="totalWeightOfItems">Total Weight Of Items</span>
          </dt>
          <dd>{serviceEntity.totalWeightOfItems}</dd>
          <dt>
            <span id="price">Price</span>
          </dt>
          <dd>{serviceEntity.price}</dd>
          <dt>
            <span id="date">Date</span>
          </dt>
          <dd>{serviceEntity.date ? <TextFormat value={serviceEntity.date} type="date" format={APP_DATE_FORMAT} /> : null}</dd>
          <dt>Location</dt>
          <dd>{serviceEntity.location ? serviceEntity.location.id : ''}</dd>
          <dt>Customer</dt>
          <dd>{serviceEntity.customer ? serviceEntity.customer.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/service" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/service/${serviceEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default ServiceDetail;
