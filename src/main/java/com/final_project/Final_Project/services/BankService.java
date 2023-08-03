package com.final_project.Final_Project.services;

import com.final_project.Final_Project.utilityClasses.JsonUtil;
import com.final_project.Final_Project.utilityClasses.OperationResult;
import com.final_project.Final_Project.entity.Balance;
import com.final_project.Final_Project.entity.TransferOperation;
import com.final_project.Final_Project.entity.UserOperation;
import com.final_project.Final_Project.enums.OperationsTypes;
import com.final_project.Final_Project.repository.BalanceRepository;
import com.final_project.Final_Project.repository.OperationsRepository;
import com.final_project.Final_Project.repository.TransferOperationsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.stream.Collectors;

import static com.final_project.Final_Project.enums.OperationsTypes.*;

@Service
@RequiredArgsConstructor
public class BankService {
    @Autowired
    private final BalanceRepository balanceRepository;
    @Autowired
    private final OperationsRepository operationsRepository;
    @Autowired
    private final TransferOperationsRepository transferOperationsRepository;

    public String getBalance(Long id) {
        if (balanceRepository.findById(id).isPresent()) {
            return JsonUtil.writeBalanceToJson(balanceRepository.findById(id).get());
        } else {
            return JsonUtil.writeOperationResultToJson(new OperationResult()
                    .setResult(-1)
                    .setOperationMessage("Данного пользователя не существует"));
        }
    }


    public String takeMoney(Long id, Long money) {
        if (money < 0) {
            return JsonUtil.writeOperationResultToJson(new OperationResult()
                    .setResult(0)
                    .setOperationMessage("Невозможно взять отрицательную сумму"));
        }
        Balance balance;
        if (balanceRepository.findById(id).isPresent()) {
            balance = balanceRepository.findById(id).get();
        } else {
            return JsonUtil.writeOperationResultToJson(new OperationResult()
                    .setResult(-1)
                    .setOperationMessage("Данного пользователя не существует"));
        }
        if (balance.getBalance() - money < 0) {
            return JsonUtil.writeOperationResultToJson(new OperationResult()
                    .setResult(0)
                    .setOperationMessage("Недостаточно средств"));
        } else {
            balance.setBalance(balance.getBalance() - money);
            balanceRepository.save(balance);
            operation_log(id, TAKE_MONEY);
            return JsonUtil.writeOperationResultToJson(new OperationResult()
                    .setResult(1)
                    .setOperationMessage("Со счета User " + id + " снято " + money));
        }
    }

    public String putMoney(Long id, Long money) {
        if (money < 0) {
            return JsonUtil.writeOperationResultToJson(new OperationResult()
                    .setResult(0)
                    .setOperationMessage("Невозможно добавить отрицательную сумму"));
        }
        Balance balance;
        if (balanceRepository.findById(id).isPresent()) {
            balance = balanceRepository.findById(id).get();
            balance.setBalance(balance.getBalance() + money);
            operation_log(id, PUT_MONEY);
            balanceRepository.save(balance);
            return JsonUtil.writeOperationResultToJson(new OperationResult()
                    .setResult(1)
                    .setOperationMessage("На счет User " + id + " добавлено " + money));
        } else {
            return JsonUtil.writeOperationResultToJson(new OperationResult()
                    .setResult(0)
                    .setOperationMessage("Данного пользователя не существует"));
        }
    }

    public String transferMoney(Long userSenderId, Long userReceiverId, Long money) {
        if (money < 0) {
            return JsonUtil.writeOperationResultToJson(new OperationResult()
                    .setResult(0)
                    .setOperationMessage("Невозможно отправить отрицательную сумму"));
        }
        if (userSenderId.equals(userReceiverId)) {
            return JsonUtil.writeOperationResultToJson(new OperationResult()
                    .setResult(0)
                    .setOperationMessage("У отправителя и получателя не может быть одинаковый id"));
        }
        if (balanceRepository.findById(userSenderId).isEmpty()) {
            return JsonUtil.writeOperationResultToJson(new OperationResult()
                    .setResult(0)
                    .setOperationMessage("Отправителя User " + userSenderId + " не существует"));
        } else if (balanceRepository.findById(userReceiverId).isEmpty()) {
            return JsonUtil.writeOperationResultToJson(new OperationResult()
                    .setResult(0)
                    .setOperationMessage("Получателя User " + userReceiverId + " не существует"));
        }
        String takeMoneyOperation = takeMoney(userSenderId, money);
        if (JsonUtil.jsonToOperationResult(takeMoneyOperation).getResult() == 0) {
            return JsonUtil.writeOperationResultToJson(new OperationResult()
                    .setResult(0)
                    .setOperationMessage("Недостаточно средств у User " + userSenderId));
        }
        if (JsonUtil.jsonToOperationResult(takeMoneyOperation).getResult() == 1) {
            operation_log(userSenderId, TRANSFER_MONEY);
            putMoney(userReceiverId, money);
            transfer_log(userSenderId, userReceiverId, money);
            return JsonUtil.writeOperationResultToJson(new OperationResult()
                    .setResult(1)
                    .setOperationMessage("User " + userSenderId + " отправил " + money + " user " + userReceiverId));
        } else {
            return JsonUtil.writeOperationResultToJson(new OperationResult()
                    .setResult(0)
                    .setOperationMessage("Произошла ошибка"));
        }
    }

    public void operation_log(Long userId, OperationsTypes operationsTypes) {
        UserOperation operation = new UserOperation();
        operation.setUserId(userId);
        operation.setOperationTypeName(operationsTypes.getOperationTypeName());
        operation.setOperationTypeNumber(operationsTypes.getOperationTypeNumber());
        operation.setTimeStamp(new Timestamp(System.currentTimeMillis()));
        operationsRepository.save(operation);
    }
    public void transfer_log(Long userSenderId, Long userReceiverId, Long transferMoney){
        TransferOperation transferOperation = new TransferOperation();
        transferOperation.setUserSenderId(userSenderId);
        transferOperation.setUserReceiverId(userReceiverId);
        transferOperation.setTransferMoney(transferMoney);
        transferOperation.setTransferOperationTime(new Timestamp(System.currentTimeMillis()));
        transferOperationsRepository.save(transferOperation);
    }

    public String getOperationList(Long userId, String fromStringDate, String toStringDate) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date fromDate;
        Date toDate;
        final Timestamp fromTimestamp;
        final Timestamp toTimestamp;
        if (fromStringDate != null && toStringDate != null) {
            try {
                fromDate = dateFormat.parse(fromStringDate);
                toDate = dateFormat.parse(toStringDate);
                fromTimestamp = new Timestamp(fromDate.getTime());
                toTimestamp = new Timestamp(toDate.getTime());
                if (fromTimestamp.after(toTimestamp)) {
                    return JsonUtil.writeOperationResultToJson(new OperationResult()
                            .setResult(-1)
                            .setOperationMessage("Дата начала не может быть больше даты окончания"));
                }
                return JsonUtil.writeOperationListToJson(operationsRepository.findAll().stream()
                        .filter(op -> op.getUserId().equals(userId))
                        .filter(op -> op.getTimeStamp().after(fromTimestamp))
                        .filter(op -> op.getTimeStamp().before(toTimestamp))
                        .collect(Collectors.toList()));
            } catch (ParseException e) {
                return JsonUtil.writeOperationResultToJson(new OperationResult()
                        .setResult(-1)
                        .setOperationMessage("Возникла ошибка с извлечением даты начала и/или окончания")
                        .setOperationError(Arrays.toString(e.getStackTrace())));
            }
        } else if (toStringDate == null && fromStringDate != null) {
            try {
                fromDate = dateFormat.parse(fromStringDate);
                fromTimestamp = new Timestamp(fromDate.getTime());
                return JsonUtil.writeOperationListToJson(operationsRepository.findAll().stream()
                        .filter(op -> op.getUserId().equals(userId))
                        .filter(op -> op.getTimeStamp().after(fromTimestamp))
                        .collect(Collectors.toList()));
            } catch (ParseException e) {
                return JsonUtil.writeOperationResultToJson(new OperationResult()
                        .setResult(-1)
                        .setOperationMessage("Возникла ошибка с извлечением даты начала")
                        .setOperationError(Arrays.toString(e.getStackTrace())));
            }
        } else if (toStringDate != null) {
            try {
                toDate = dateFormat.parse(toStringDate);
                toTimestamp = new Timestamp(toDate.getTime());
                return JsonUtil.writeOperationListToJson(operationsRepository.findAll().stream()
                        .filter(op -> op.getUserId().equals(userId))
                        .filter(op -> op.getTimeStamp().before(toTimestamp))
                        .collect(Collectors.toList()));
            } catch (ParseException e) {
                return JsonUtil.writeOperationResultToJson(new OperationResult()
                        .setResult(-1)
                        .setOperationMessage("Возникла ошибка с извлечением даты окончания")
                        .setOperationError(Arrays.toString(e.getStackTrace())));
            }
        }else {
            return JsonUtil.writeOperationListToJson(operationsRepository.findAll().stream()
                    .filter(op -> op.getUserId().equals(userId))
                    .collect(Collectors.toList()));
        }
    }

}
