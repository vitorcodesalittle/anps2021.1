import { stringify } from 'querystring'

export type PropertyValidator<T, P> = (data: T, value: P) => Either<string, boolean>
export type SchemaProperty<T, P> = {
    validate?: PropertyValidator<T, P>;
    type?: 'text' | 'number';
    htmlType: 'text' | 'password' | 'number' | 'date';
    label: string;
    order: number;
    onChange: (data: T, value: string) => T;
}
export type Schema<T extends {}> = {
    [P in keyof T]: SchemaProperty<T, T[P]>
}

export const fail = (reason: string): Either<string, boolean> => createEither<string, boolean>(reason, false)
export const success = (): Either<string, boolean> => createEither<string, boolean>(undefined, true)

