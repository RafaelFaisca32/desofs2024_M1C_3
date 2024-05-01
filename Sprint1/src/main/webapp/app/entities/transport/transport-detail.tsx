import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './transport.reducer';

export const TransportDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const transportEntity = useAppSelector(state => state.transport.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="transportDetailsHeading">Transport</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">Id</span>
          </dt>
          <dd>{transportEntity.id}</dd>
          <dt>
            <span id="startTime">Start Time</span>
          </dt>
          <dd>
            {transportEntity.startTime ? <TextFormat value={transportEntity.startTime} type="date" format={APP_DATE_FORMAT} /> : null}
          </dd>
          <dt>
            <span id="endTime">End Time</span>
          </dt>
          <dd>{transportEntity.endTime ? <TextFormat value={transportEntity.endTime} type="date" format={APP_DATE_FORMAT} /> : null}</dd>
          <dt>Location</dt>
          <dd>{transportEntity.location ? transportEntity.location.id : ''}</dd>
          <dt>Driver</dt>
          <dd>{transportEntity.driver ? transportEntity.driver.id : ''}</dd>
          <dt>Service</dt>
          <dd>{transportEntity.service ? transportEntity.service.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/transport" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/transport/${transportEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default TransportDetail;
