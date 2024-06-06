import axios from 'axios';
import { createAsyncThunk, isFulfilled, isPending } from '@reduxjs/toolkit';
import { ASC } from 'app/shared/util/pagination.constants';
import { cleanEntity } from 'app/shared/util/entity-utils';
import { IQueryParams, createEntitySlice, EntityState, serializeAxiosError } from 'app/shared/reducers/reducer.utils';
import { IServiceRequest, defaultValue } from 'app/shared/model/service-request.model';

const initialState: EntityState<IServiceRequest> = {
  loading: false,
  errorMessage: null,
  entities: [],
  entity: defaultValue,
  updating: false,
  updateSuccess: false,
};

const apiUrl = 'api/service-requests';

// Actions

export const getEntities = createAsyncThunk('serviceRequest/fetch_entity_list', async ({ sort }: IQueryParams) => {
  const requestUrl = `${apiUrl}?${sort ? `sort=${sort}&` : ''}cacheBuster=${new Date().getTime()}`;
  return axios.get<IServiceRequest[]>(requestUrl);
});

export const getEntitiesByUserLoggedIn = createAsyncThunk('serviceRequest/fetch_entity_list', async ({ sort }: IQueryParams) => {
  const requestUrl = `${apiUrl}/getByUserLoggedIn?${sort ? `sort=${sort}&` : ''}cacheBuster=${new Date().getTime()}`;
  return axios.get<IServiceRequest[]>(requestUrl);
});

export const getEntity = createAsyncThunk(
  'serviceRequest/fetch_entity',
  async (id: string | number) => {
    const requestUrl = `${apiUrl}/${id}`;
    return axios.get<IServiceRequest>(requestUrl);
  },
  { serializeError: serializeAxiosError },
);

export const createEntity = createAsyncThunk(
  'serviceRequest/create_entity',
  async (entity: IServiceRequest, thunkAPI) => {
    const result = await axios.post<IServiceRequest>(apiUrl, cleanEntity(entity));
    thunkAPI.dispatch(getEntities({}));
    return result;
  },
  { serializeError: serializeAxiosError },
);

export const updateEntity = createAsyncThunk(
  'serviceRequest/update_entity',
  async (entity: IServiceRequest, thunkAPI) => {
    const result = await axios.put<IServiceRequest>(`${apiUrl}/${entity.id}`, cleanEntity(entity));
    thunkAPI.dispatch(getEntities({}));
    return result;
  },
  { serializeError: serializeAxiosError },
);

export const partialUpdateEntity = createAsyncThunk(
  'serviceRequest/partial_update_entity',
  async (entity: IServiceRequest, thunkAPI) => {
    const result = await axios.patch<IServiceRequest>(`${apiUrl}/${entity.id}`, cleanEntity(entity));
    thunkAPI.dispatch(getEntities({}));
    return result;
  },
  { serializeError: serializeAxiosError },
);

export const deleteEntity = createAsyncThunk(
  'serviceRequest/delete_entity',
  async (id: string | number, thunkAPI) => {
    const requestUrl = `${apiUrl}/${id}`;
    const result = await axios.delete<IServiceRequest>(requestUrl);
    thunkAPI.dispatch(getEntities({}));
    return result;
  },
  { serializeError: serializeAxiosError },
);

export const updateEntityStatus = createAsyncThunk(
  'serviceRequest/update_entity_status',
  async ({ id, isApproved, driverId, startDate, endDate }: { id: string | number; isApproved: boolean, driverId :string, startDate : string , endDate : string }, thunkAPI) => {
    const requestUrl = isApproved ? `${apiUrl}/${id}/${isApproved}/${driverId}/${startDate}/${endDate}` : `${apiUrl}/${id}/${isApproved}` ;
    const result = await axios.put<IServiceRequest>(requestUrl, { isApproved, driverId, startDate, endDate });
    thunkAPI.dispatch(getEntities({}));
    return result.data;
  },
  { serializeError: serializeAxiosError },
);

// slice

export const ServiceRequestSlice = createEntitySlice({
  name: 'serviceRequest',
  initialState,
  extraReducers(builder) {
    builder
      .addCase(getEntity.fulfilled, (state, action) => {
        state.loading = false;
        state.entity = action.payload.data;
      })
      .addCase(deleteEntity.fulfilled, state => {
        state.updating = false;
        state.updateSuccess = true;
        state.entity = {};
      })
      .addMatcher(isFulfilled(getEntities), (state, action) => {
        const { data } = action.payload;

        return {
          ...state,
          loading: false,
          entities: data.sort((a, b) => {
            if (!action.meta?.arg?.sort) {
              return 1;
            }
            const order = action.meta.arg.sort.split(',')[1];
            const predicate = action.meta.arg.sort.split(',')[0];
            return order === ASC ? (a[predicate] < b[predicate] ? -1 : 1) : b[predicate] < a[predicate] ? -1 : 1;
          }),
        };
      })
      .addMatcher(isFulfilled(createEntity, updateEntity, partialUpdateEntity), (state, action) => {
        state.updating = false;
        state.loading = false;
        state.updateSuccess = true;
        state.entity = action.payload.data;
      })
      .addMatcher(isPending(getEntities, getEntity), state => {
        state.errorMessage = null;
        state.updateSuccess = false;
        state.loading = true;
      })
      .addMatcher(isPending(createEntity, updateEntity, partialUpdateEntity, deleteEntity), state => {
        state.errorMessage = null;
        state.updateSuccess = false;
        state.updating = true;
      });
  },
});

export const { reset } = ServiceRequestSlice.actions;

// Reducer
export default ServiceRequestSlice.reducer;
