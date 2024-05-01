import { ICustomer } from 'app/shared/model/customer.model';

export interface ILocation {
  id?: string;
  coordX?: number | null;
  coordY?: number | null;
  coordZ?: number | null;
  customer?: ICustomer | null;
}

export const defaultValue: Readonly<ILocation> = {};
