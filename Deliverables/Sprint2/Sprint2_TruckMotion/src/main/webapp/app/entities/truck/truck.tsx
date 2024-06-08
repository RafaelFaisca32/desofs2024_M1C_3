import React, { useState, useEffect } from 'react';
import { Link, useLocation, useNavigate } from 'react-router-dom';
import { Button, Table } from 'reactstrap';
import { Translate, getSortState } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faSort, faSortUp, faSortDown } from '@fortawesome/free-solid-svg-icons';
import { ASC, DESC, SORT } from 'app/shared/util/pagination.constants';
import { overrideSortStateWithQueryParams } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';
import { AUTHORITIES } from '../../config/constants';

import { getEntities } from './truck.reducer';

export const Truck = () => {
  const dispatch = useAppDispatch();

  const pageLocation = useLocation();
  const navigate = useNavigate();

  const [sortState, setSortState] = useState(overrideSortStateWithQueryParams(getSortState(pageLocation, 'id'), pageLocation.search));

  const truckList = useAppSelector(state => state.truck.entities);
  const loading = useAppSelector(state => state.truck.loading);

  const account = useAppSelector(state => state.authentication.account);
  const managerRole = [AUTHORITIES.MANAGER];
  const authorities = account.authorities as string[]

  const getAllEntities = () => {
    dispatch(
      getEntities({
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

  return (
    <div>
      <h2 id="truck-heading" data-cy="TruckHeading">
        Trucks
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} /> Refresh list
          </Button>
          {managerRole.some(value => authorities.includes(value))  ? 
                    <Link to="/truck/new" className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
                    <FontAwesomeIcon icon="plus" />
                    &nbsp; Create a new Truck
                  </Link>
          : null}
        </div>
      </h2>
      <div className="table-responsive">
        {truckList && truckList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th >
                  Truck Number 
                </th>
                <th className="hand" onClick={sort('make')}>
                  Make <FontAwesomeIcon icon={getSortIconByFieldName('make')} />
                </th>
                <th className="hand" onClick={sort('model')}>
                  Model <FontAwesomeIcon icon={getSortIconByFieldName('model')} />
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {truckList.map((truck, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`/truck/${truck.id}`} color="link" size="sm">
                      {i}
                    </Button>
                  </td>
                  <td>{truck.make}</td>
                  <td>{truck.model}</td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`/truck/${truck.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                        <FontAwesomeIcon icon="eye" /> <span className="d-none d-md-inline">View</span>
                      </Button>
                      {managerRole.some(value => authorities.includes(value))  ?
                      <><Button tag={Link} to={`/truck/${truck.id}/edit`} color="primary" size="sm" data-cy="entityEditButton">
                          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
                        </Button><Button
                          onClick={() => (window.location.href = `/truck/${truck.id}/delete`)}
                          color="danger"
                          size="sm"
                          data-cy="entityDeleteButton"
                        >
                            <FontAwesomeIcon icon="trash" /> <span className="d-none d-md-inline">Delete</span>
                          </Button></>
                       : null}
                    </div>
                  </td>
                </tr>
              ))}
            </tbody>
          </Table>
        ) : (
          !loading && <div className="alert alert-warning">No Trucks found</div>
        )}
      </div>
    </div>
  );
};

export default Truck;
