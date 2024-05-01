import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import {} from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './driver.reducer';

export const DriverDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const driverEntity = useAppSelector(state => state.driver.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="driverDetailsHeading">Driver</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">Id</span>
          </dt>
          <dd>{driverEntity.id}</dd>
          <dt>Truck</dt>
          <dd>{driverEntity.truck ? driverEntity.truck.id : ''}</dd>
          <dt>Application User</dt>
          <dd>{driverEntity.applicationUser ? driverEntity.applicationUser.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/driver" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/driver/${driverEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default DriverDetail;
