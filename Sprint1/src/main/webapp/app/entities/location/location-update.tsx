import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { ICustomer } from 'app/shared/model/customer.model';
import { getEntities as getCustomers } from 'app/entities/customer/customer.reducer';
import { ILocation } from 'app/shared/model/location.model';
import { getEntity, updateEntity, createEntity, reset } from './location.reducer';

export const LocationUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const customers = useAppSelector(state => state.customer.entities);
  const locationEntity = useAppSelector(state => state.location.entity);
  const loading = useAppSelector(state => state.location.loading);
  const updating = useAppSelector(state => state.location.updating);
  const updateSuccess = useAppSelector(state => state.location.updateSuccess);

  const handleClose = () => {
    navigate('/location');
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }

    dispatch(getCustomers({}));
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  // eslint-disable-next-line complexity
  const saveEntity = values => {
    if (values.coordX !== undefined && typeof values.coordX !== 'number') {
      values.coordX = Number(values.coordX);
    }
    if (values.coordY !== undefined && typeof values.coordY !== 'number') {
      values.coordY = Number(values.coordY);
    }
    if (values.coordZ !== undefined && typeof values.coordZ !== 'number') {
      values.coordZ = Number(values.coordZ);
    }

    const entity = {
      ...locationEntity,
      ...values,
      customer: customers.find(it => it.id.toString() === values.customer?.toString()),
    };

    if (isNew) {
      dispatch(createEntity(entity));
    } else {
      dispatch(updateEntity(entity));
    }
  };

  const defaultValues = () =>
    isNew
      ? {}
      : {
          ...locationEntity,
          customer: locationEntity?.customer?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="truckMotionApp.location.home.createOrEditLabel" data-cy="LocationCreateUpdateHeading">
            Create or edit a Location
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <ValidatedForm defaultValues={defaultValues()} onSubmit={saveEntity}>
              {!isNew ? <ValidatedField name="id" required readOnly id="location-id" label="Id" validate={{ required: true }} /> : null}
              <ValidatedField label="Coord X" id="location-coordX" name="coordX" data-cy="coordX" type="text" />
              <ValidatedField label="Coord Y" id="location-coordY" name="coordY" data-cy="coordY" type="text" />
              <ValidatedField label="Coord Z" id="location-coordZ" name="coordZ" data-cy="coordZ" type="text" />
              <ValidatedField id="location-customer" name="customer" data-cy="customer" label="Customer" type="select">
                <option value="" key="0" />
                {customers
                  ? customers.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/location" replace color="info">
                <FontAwesomeIcon icon="arrow-left" />
                &nbsp;
                <span className="d-none d-md-inline">Back</span>
              </Button>
              &nbsp;
              <Button color="primary" id="save-entity" data-cy="entityCreateSaveButton" type="submit" disabled={updating}>
                <FontAwesomeIcon icon="save" />
                &nbsp; Save
              </Button>
            </ValidatedForm>
          )}
        </Col>
      </Row>
    </div>
  );
};

export default LocationUpdate;
