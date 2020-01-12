CREATE OR REPLACE PROCEDURE transfer(senderAccountID integer, receiverAccountID integer, amount float)
    LANGUAGE plpgsql
    AS $$
    DECLARE
        current_balance float;
    BEGIN
        SELECT balance INTO current_balance FROM account WHERE id = senderAccountID;

        if current_balance < amount THEN
            RAISE EXCEPTION 'Transfer failed: current balance % < %', current_balance, amount;
        END IF;

        -- subtracting the amount from the sender's account
        UPDATE account
        SET balance = balance - amount
        WHERE id = senderAccountID;

        -- adding the amount to the receiver's account
        UPDATE account
        SET balance = balance + amount
        WHERE id = receiverAccountID;

        COMMIT;

        RAISE INFO 'Transfer Total: % from account % to %', amount, senderAccountID, receiverAccountID;
    END; $$;