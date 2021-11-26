import React from 'react'

interface TransactionCardProps {
    transaction: Sale | Purchase
}

function TransactionCard(props: TransactionCardProps) {

    return (
        <div>
            this will be a transaction
            <pre>{JSON.stringify(props.transaction)}</pre>
        </div>
    )
}

export default TransactionCard