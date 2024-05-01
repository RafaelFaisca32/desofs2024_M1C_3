import dayjs from 'dayjs';
import { ILocation } from 'app/shared/model/location.model';
import { ICustomer } from 'app/shared/model/customer.model';

export interface IService {
  id?: string;
  items?: string | null;
  serviceName?: string | null;
  totalWeightOfItems?: number | null;
  price?: number | null;
  date?: dayjs.Dayjs | null;
  location?: ILocation | null;
  customer?: ICustomer | null;
}

export const defaultValue: Readonly<IService> = {};
