import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import {} from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './location.reducer';

export const LocationDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const locationEntity = useAppSelector(state => state.location.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="locationDetailsHeading">Location</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="coordX">Coord X</span>
          </dt>
          <dd>{locationEntity.coordX}</dd>
          <dt>
            <span id="coordY">Coord Y</span>
          </dt>
          <dd>{locationEntity.coordY}</dd>
          <dt>
            <span id="coordZ">Coord Z</span>
          </dt>
          <dd>{locationEntity.coordZ}</dd>
          <dt>Customer</dt>
          <dd>{locationEntity.customer ? locationEntity.customer.company : ''}</dd>
        </dl>
        <Button tag={Link} to="/location" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/location/${locationEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default LocationDetail;
