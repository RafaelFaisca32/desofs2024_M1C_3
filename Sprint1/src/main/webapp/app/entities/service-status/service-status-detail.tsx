import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './service-status.reducer';

export const ServiceStatusDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const serviceStatusEntity = useAppSelector(state => state.serviceStatus.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="serviceStatusDetailsHeading">Service Status</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">Id</span>
          </dt>
          <dd>{serviceStatusEntity.id}</dd>
          <dt>
            <span id="date">Date</span>
          </dt>
          <dd>{serviceStatusEntity.date ? <TextFormat value={serviceStatusEntity.date} type="date" format={APP_DATE_FORMAT} /> : null}</dd>
          <dt>
            <span id="observations">Observations</span>
          </dt>
          <dd>{serviceStatusEntity.observations}</dd>
          <dt>
            <span id="status">Status</span>
          </dt>
          <dd>{serviceStatusEntity.status}</dd>
          <dt>Service</dt>
          <dd>{serviceStatusEntity.service ? serviceStatusEntity.service.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/service-status" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/service-status/${serviceStatusEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default ServiceStatusDetail;
