interface Either<L, R> {
    left: L | undefined,
    right: R | undefined,
    isLeft: boolean,
}

const ifIsRightElse = <L, R, RL, RR>(either: Either<L, R>, rightHandler: (r: R) => RR, leftHandler: (l: L) => RL) => {
    if (either.isLeft) return leftHandler(either.left!)
    else return rightHandler(either.right!)
}

const createEither = <L, R>(left?: L, right?: R): Either<L, R> => {
    if (left === undefined && right === undefined)
    if (left !== undefined) return { left, right: undefined, isLeft: true}
    return { left: undefined, right, isLeft: false}
}