export interface ITruck {
  id?: string;
  make?: string | null;
  model?: string | null;
}

export const defaultValue: Readonly<ITruck> = {};
