import { IUser } from 'app/shared/model/user.model';

export interface IManager {
  id?: string;
  user?: IUser | null;
}

export const defaultValue: Readonly<IManager> = {};
