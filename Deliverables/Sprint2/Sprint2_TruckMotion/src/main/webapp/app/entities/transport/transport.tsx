import React, { useState, useEffect } from 'react';
import { Link, useLocation, useNavigate } from 'react-router-dom';
import { Button, Table } from 'reactstrap';
import { Translate, TextFormat, getSortState } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faSort, faSortUp, faSortDown } from '@fortawesome/free-solid-svg-icons';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { ASC, DESC, SORT } from 'app/shared/util/pagination.constants';
import { overrideSortStateWithQueryParams } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntitiesByUserLoggedIn, partialUpdateEntity } from './transport.reducer';
import { updateEntityStatus } from 'app/entities/service-request/service-request.reducer';
import dayjs from 'dayjs';

export const Transport = () => {
  const dispatch = useAppDispatch();

  const pageLocation = useLocation();
  const navigate = useNavigate();

  const [sortState, setSortState] = useState(overrideSortStateWithQueryParams(getSortState(pageLocation, 'id'), pageLocation.search));

  const transportList = useAppSelector(state => state.transport.entities);
  const loading = useAppSelector(state => state.transport.loading);

  const getAllEntities = () => {
    dispatch(
      getEntitiesByUserLoggedIn({
        sort: `${sortState.sort},${sortState.order}`,
      }),
    );
  };

  const sortEntities = () => {
    getAllEntities();
    const endURL = `?sort=${sortState.sort},${sortState.order}`;
    if (pageLocation.search !== endURL) {
      navigate(`${pageLocation.pathname}${endURL}`);
    }
  };

  useEffect(() => {
    sortEntities();
  }, [sortState.order, sortState.sort]);

  const sort = p => () => {
    setSortState({
      ...sortState,
      order: sortState.order === ASC ? DESC : ASC,
      sort: p,
    });
  };

  const handleSyncList = () => {
    sortEntities();
  };

  const getSortIconByFieldName = (fieldName: string) => {
    const sortFieldName = sortState.sort;
    const order = sortState.order;
    if (sortFieldName !== fieldName) {
      return faSort;
    } else {
      return order === ASC ? faSortUp : faSortDown;
    }
  };

  const startService = (id) => {
    const updatedEntity = {
      id,
      startTime: dayjs(),
      endTime: null,
    };

    dispatch(partialUpdateEntity(updatedEntity));
  };

  const endService = (id) => {
    const updatedEntity = {
      id,
      endTime: dayjs(),
    };

    dispatch(partialUpdateEntity(updatedEntity));
  };

  return (
    <div>
      <h2 id="transport-heading" data-cy="TransportHeading">
        Transports
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} /> Refresh list
          </Button>
        </div>
      </h2>
      <div className="table-responsive">
        {transportList && transportList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th>
                  Transport option <FontAwesomeIcon icon={getSortIconByFieldName('startTime')} />
                </th>
                <th className="hand" onClick={sort('startTime')}>
                  Start Time <FontAwesomeIcon icon={getSortIconByFieldName('startTime')} />
                </th>
                <th className="hand" onClick={sort('endTime')}>
                  End Time <FontAwesomeIcon icon={getSortIconByFieldName('endTime')} />
                </th>
                <th>
                  Location Coordinates(x,y,z)<FontAwesomeIcon icon="sort" />
                </th>
                <th>
                  Service Request Name <FontAwesomeIcon icon="sort" />
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {transportList.map((transport, i) => {
                let buttonColor = 'green';
                let buttonText = 'Start Service';
                let onClickHandler = () => startService(transport.id);

                if (transport.startTime && !transport.endTime) {
                  buttonColor = 'yellow';
                  buttonText = 'In Progress';
                  onClickHandler = () => endService(transport.id);
                } else if (transport.startTime && transport.endTime) {
                  buttonColor = 'red';
                  buttonText = 'Completed';
                }

                return (
                  <tr key={`entity-${i}`} data-cy="entityTable">
                    <td>
                      <Button tag={Link} to={`/transport/${transport.id}`} color="link" size="sm">
                        {i}
                      </Button>
                    </td>
                    <td>{transport.startTime ? <TextFormat type="date" value={transport.startTime} format={APP_DATE_FORMAT} /> : null}</td>
                    <td>{transport.endTime ? <TextFormat type="date" value={transport.endTime} format={APP_DATE_FORMAT} /> : null}</td>
                    <td>{transport.location ? <Link to={`/location/${transport.location.id}`}>{transport.location.coordX + "--" + transport.location.coordY + "--" + transport.location.coordZ}</Link> : ''}</td>
                    <td>
                      {transport.serviceRequest ? (
                        <Link to={`/service-request/${transport.serviceRequest.id}`}>{transport.serviceRequest.serviceName}</Link>
                      ) : (
                        ''
                      )}
                    </td>
                    <td className="text-end">
                      <div className="btn-group flex-btn-group-container">
                        <Button
                          onClick={onClickHandler}
                          replace
                          style={{
                            backgroundColor: buttonColor,
                            borderColor: buttonColor,
                            color: 'white',
                          }}
                        >
                          <FontAwesomeIcon icon="plus" /> <span className="d-none d-md-inline">{buttonText}</span>
                        </Button>
                        <Button tag={Link} to={`/transport/${transport.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                          <FontAwesomeIcon icon="eye" /> <span className="d-none d-md-inline">View</span>
                        </Button>
                        <Button tag={Link} to={`/transport/${transport.id}/edit`} color="primary" size="sm" data-cy="entityEditButton">
                          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
                        </Button>
                      </div>
                    </td>
                  </tr>
                );
              })}
            </tbody>
          </Table>
        ) : (
          !loading && <div className="alert alert-warning">No Transports found</div>
        )}
      </div>
    </div>
  );
};

export default Transport;
