import { FC } from 'react'
import { stringify } from 'querystring'

export type PropertyValidator<T, P> = (data: T, value: P) => Either<string, boolean>
export type SchemaProperty<T, P, H, C extends FC<{onChange: (h: H) => any}>> = {
    validate?: PropertyValidator<T, P>;
    type?: 'text' | 'number';
    htmlType: 'text' | 'password' | 'number' | 'date';
    label: string;
    order: number;
    render?: C;
    onChange: (data: T, value: H) => T;
}
export type Schema<T extends Record<string, unknown>> = {
    [P in keyof T]?: SchemaProperty<T, T[P], any, any>
}

export const fail = (reason: string): Either<string, boolean> => createEither<string, boolean>(reason, false)
export const success = (): Either<string, boolean> => createEither<string, boolean>(undefined, true)

