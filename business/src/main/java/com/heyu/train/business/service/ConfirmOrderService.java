package com.heyu.train.business.service;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.util.EnumUtil;
import cn.hutool.core.util.NumberUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.heyu.train.business.domain.*;
import com.heyu.train.business.enums.ConfirmOrderEnum;
import com.heyu.train.business.enums.SeatTypeEnum;
import com.heyu.train.business.enums.TrainSeatEnum;
import com.heyu.train.business.mapper.ConfirmOrderMapper;
import com.heyu.train.business.mapper.DailyTrainMapper;
import com.heyu.train.business.mapper.DailyTrainTicketMapper;
import com.heyu.train.business.req.ConfirmOrderDoReq;
import com.heyu.train.business.req.ConfirmOrderQueryReq;
import com.heyu.train.business.req.ConfirmOrderTicketReq;
import com.heyu.train.common.constant.BizExceptionEnum;
import com.heyu.train.common.exception.BizException;
import com.heyu.train.common.resp.ConfirmOrderQueryResp;
import com.heyu.train.common.util.SnowFlask;
import com.heyu.train.generator.generator.help.MyBatisWrapper;
import com.heyu.train.generator.generator.help.PageInfo;
import context.LoginMemberContext;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.bouncycastle.util.Strings;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static com.heyu.train.business.domain.ConfirmOrderField.*;

/**
 * 功能:
 * 作者:何宇
 * 日期：2024/3/23 12:53
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class ConfirmOrderService {
    final ConfirmOrderMapper confirmOrderMapper;
    final DailyTrainMapper dailyTrainMapper;
    final DailyTrainTicketMapper dailyTrainTicketMapper;
    final DailyTrainCarriageService dailyTrainCarriageService;
    final DailyTrainSeatService dailyTrainSeatService;
    final AfterConfirmOrderService afterConfirmOrderService;

    public void save(ConfirmOrderDoReq req) {
        ConfirmOrder confirmOrder = BeanUtil.copyProperties(req, ConfirmOrder.class);
        DateTime now = DateTime.now();
        if (ObjectUtil.isNull(confirmOrder.getId())) {
            confirmOrder.setCreateTime(now);
            confirmOrder.setUpdateTime(now);
            confirmOrder.setId(SnowFlask.getSnowFlaskId());
            ConfirmOrder pass = BeanUtil.copyProperties(req, ConfirmOrder.class);
            confirmOrderMapper.insert(pass);
        } else {
            confirmOrder.setUpdateTime(now);
            confirmOrderMapper.updateByPrimaryKey(confirmOrder);
        }

    }

    public PageInfo<ConfirmOrderQueryResp> queryList(ConfirmOrderQueryReq req) {
        MyBatisWrapper<ConfirmOrderQueryResp> wrapper = new MyBatisWrapper<>();
        wrapper.select(Id, MemberId, Date, TrainCode, Start, TrainCode, End, DailyTrainTicketId, Status, Tickets);
        log.info("pageSize:{}----pageNum:{},", req.getPageSize(), req.getPageNum());
        int total = confirmOrderMapper.list(wrapper).size();
        List<ConfirmOrder> list = confirmOrderMapper.list(wrapper.limit((req.getPageNum() - 1) * req.getPageSize(), req.getPageSize()));
        List<ConfirmOrderQueryResp> resp = BeanUtil.copyToList(list, ConfirmOrderQueryResp.class);
        return new PageInfo<>(req.getPageNum(), req.getPageSize(), total, resp);
    }

    public void del(Long id) {
        confirmOrderMapper.deleteByPrimaryKey(id);
    }

    public void doConfirm(ConfirmOrderDoReq req) {
        MyBatisWrapper<DailyTrain> wrapper = new MyBatisWrapper<>();
        wrapper.select(DailyTrainField.Id).whereBuilder().andEq(DailyTrainField.setDate(req.getDate())).andEq(DailyTrainField.setCode(req.getTrainCode()));
        DailyTrain dailyTrain = dailyTrainMapper.topOne(wrapper);
        if (Objects.isNull(dailyTrain)) {
            throw new BizException(BizExceptionEnum.NO_TRAINS);
        }
        MyBatisWrapper<DailyTrainTicket> wrapper01 = new MyBatisWrapper<>();
        wrapper01.select(DailyTrainTicketField.Id).whereBuilder().andEq(DailyTrainTicketField.setDate(req.getDate())).andEq(DailyTrainTicketField.setTrainCode(req.getTrainCode())).andEq(DailyTrainTicketField.setStart(req.getStart())).andEq(DailyTrainTicketField.setEnd(req.getEnd()));
        DailyTrainTicket dailyTrainTicket = dailyTrainTicketMapper.topOne(wrapper01);
        if (Objects.isNull(dailyTrainTicket)) {
            throw new BizException(BizExceptionEnum.NO_TICKETS);
        }
        List<ConfirmOrderTicketReq> lists = req.getTickets();
        //插入订单表
        ConfirmOrder confirmOrder = BeanUtil.copyProperties(req, ConfirmOrder.class);
        DateTime now = DateTime.now();
        confirmOrder.setId(SnowFlask.getSnowFlaskId());
        confirmOrder.setUpdateTime(now);
        confirmOrder.setMemberId(LoginMemberContext.getId());
        confirmOrder.setCreateTime(now);
        confirmOrder.setStatus(ConfirmOrderEnum.INIT.getCode());
        confirmOrder.setTickets(JSONUtil.toJsonStr(req.getTickets()));
        confirmOrderMapper.insert(confirmOrder);
        //查询余票
        MyBatisWrapper<DailyTrainTicket> tikcetWrapper = new MyBatisWrapper<>();
        tikcetWrapper.select(DailyTrainTicketField.TrainCode, DailyTrainTicketField.Yw, DailyTrainTicketField.Ydz, DailyTrainTicketField.Edz, DailyTrainTicketField.StartIndex, DailyTrainTicketField.Rw, DailyTrainTicketField.EndIndex).whereBuilder().andEq(DailyTrainTicketField.setDate(req.getDate())).andEq(DailyTrainTicketField.setTrainCode(req.getTrainCode())).andEq(DailyTrainTicketField.setStart(req.getStart())).andEq(DailyTrainTicketField.setEnd(req.getEnd()));
        DailyTrainTicket dbTicket = dailyTrainTicketMapper.topOne(tikcetWrapper);
        log.info("ticket信息:{}", dbTicket);
        //预扣减余票
        reduceTickets(req, dbTicket);
        //保存最终选座信息
        List<DailyTrainSeat> finalSeats = new ArrayList<>();
        //模拟出选座,并计算机出牌偏移量
        if (StrUtil.isNotBlank(lists.get(0).getSeat())) {
            log.info("本次有选座");
            List<String> colsByType = SeatTypeEnum.getColsByType(lists.get(0).getSeatTypeCode());
            ArrayList<String> seats = new ArrayList<>();
            for (int i = 1; i <= 2; i++) {
                for (String col : colsByType)
                    seats.add(col + i);
            }
            log.info("选座信息:{}", seats);
            ArrayList<Integer> absoluteCol = new ArrayList<>();
            for (ConfirmOrderTicketReq ticketReq : lists) {
                int i = seats.indexOf(ticketReq.getSeat());
                absoluteCol.add(i);
            }
            log.info("绝对选座:{}", absoluteCol);
            ArrayList<Integer> offset = new ArrayList<>();
            for (int i = 0; i < absoluteCol.size(); i++) {
                int j = absoluteCol.get(i) - absoluteCol.get(0);
                offset.add(j);
            }
            log.info("偏移量:{}", offset);
            getSeats(finalSeats, req.getDate(), req.getTrainCode(), req.getTickets().get(0).getSeatTypeCode(), req.getTickets().get(0).getSeat().split("")[0], offset, dbTicket.getStartIndex(), dbTicket.getEndIndex());
        } else {
            log.info("本次没有选座");
            for (ConfirmOrderTicketReq ticket : req.getTickets()) {
                getSeats(finalSeats,
                        req.getDate(),
                        req.getTrainCode(),
                        ticket.getSeatTypeCode(),
                        null, null,
                        dbTicket.getStartIndex(),
                        dbTicket.getEndIndex()
                );

            }
        }

        log.info("最终选座:{}", finalSeats.stream().map(DailyTrainSeat::getCarriageSeatIndex).collect(Collectors.toList()));
        afterConfirmOrderService.afterConfirm(finalSeats);
    }

    private void getSeats(List<DailyTrainSeat> finalChooseSeats, Date date, String trainCode, String seatType, String columnFirst, List<Integer> offsetList, Integer startIndex, Integer endIndex) {
        List<DailyTrainCarriage> carriageList = dailyTrainCarriageService.getBySeatType(trainCode, date, seatType);

        for (DailyTrainCarriage dailyTrainCarriage : carriageList) {
            //每次在遍历下一个车厢重新选座,即清空最终车座表
//            finalChooseSeats.clear();
            log.info("从车厢{}开始选", dailyTrainCarriage.getIndex());
            List<DailyTrainSeat> seatLists = dailyTrainSeatService.getSeatByCarriageIndex(date, trainCode, dailyTrainCarriage.getIndex());
            log.info("车厢{}有{}个座位", dailyTrainCarriage.getIndex(), seatLists.size());
            log.info("车座:{}", seatLists);
            log.info("----------------------------------------------------");
            for (DailyTrainSeat seat : seatLists) {
                boolean isAlreadySelect = false;
                for (DailyTrainSeat dt : finalChooseSeats) {
                    if (seat.getId().equals(dt.getId())) {
                        isAlreadySelect = true;
                        break;
                    }
                }
                if (isAlreadySelect) {
                    continue;
                }
                Integer seatIndex = seat.getCarriageSeatIndex();
                String col = seat.getCol();
                if (StrUtil.isBlank(columnFirst)) {
                    log.info("无选座");
                } else {
                    log.info("有选座!!");
                    if (!col.equals(columnFirst)) {
                        log.info("当前座位{}不匹配,继续匹配下一个,当前座位列:{},目标座位列:{}", seatIndex, seat.getCol(), columnFirst);
                        continue;
                    }
                }
                if (isValid(seat, startIndex, endIndex)) {
                    log.info("座位{}满足条件", seat.getCarriageSeatIndex());
                    finalChooseSeats.add(seat);
                } else {
                    log.info("座位{}在区间{}~{}已经选过票,不满足条件", startIndex, endIndex, seat.getCarriageSeatIndex());
                    continue;
                }
                //如果没有选座,以上操作就已经完成了,如果已经选过座位,则需要继续以下判断
                boolean isGetAllSeats = true;

                if (CollUtil.isNotEmpty(offsetList)) {
                    log.info("偏移量:{}", offsetList);
                    for (int i = 1; i <= offsetList.size() - 1; i++) {
                        Integer offset = offsetList.get(i);
                        int nextIndex = offset + seatIndex - 1;
                        if (nextIndex > seatLists.size() - 1) {
                            log.info("车厢{}已满,继续寻找下个车厢", seat.getCarriageIndex());
                            //isGetAllSeats是为了跳出循环
                            isGetAllSeats = false;
                            break;
                        }
                        DailyTrainSeat nextSeat = seatLists.get(nextIndex);
                        boolean isChooseNext = isValid(nextSeat, startIndex, endIndex);
                        if (isChooseNext) {
                            log.info("已选择座位{}", nextIndex + 1);
                            finalChooseSeats.add(nextSeat);
                        } else {
                            log.info("座位{}不符合", nextIndex + 1);
                            isGetAllSeats = false;
                            break;
                        }
                    }

                }
                //
                if (!isGetAllSeats) {
                    //如哦isGetAllSeats为false,表示在有选座的/情况下,至少有1个座位不符合,需要清除之前选择的座位,进行下一轮循环
                    finalChooseSeats.clear();
                    continue;
                }
                return;
            }
        }
    }

    //校验每个座位是否满足
    public boolean isValid(DailyTrainSeat seat, Integer startIndex, Integer endIndex) {
        String preSell = seat.getSell();
        String sellPart = preSell.substring(startIndex, endIndex);
        if (Integer.parseInt(sellPart) > 0) {
            log.info("选座失败，有座位已经被售出");
            return false;
        } else {
            log.info("选座成功");
        }
        String curSell = sellPart.replace('0', '1');
        curSell = StrUtil.fillBefore(curSell, '0', endIndex);
        curSell = StrUtil.fillAfter(curSell, '0', preSell.length());
        int i = NumberUtil.binaryToInt(curSell) | NumberUtil.binaryToInt(preSell);
        String finalSell = NumberUtil.getBinaryStr(i);
        finalSell = StrUtil.fillBefore(finalSell, '0', preSell.length());
        log.info("座位{}被选中,原售票信息:{},车站区间:{}~{},即{},最终售票信息:{}", seat.getCarriageSeatIndex(), preSell, startIndex, endIndex, curSell, finalSell);
        seat.setSell(finalSell);
        return true;
    }

    private static void reduceTickets(ConfirmOrderDoReq req, DailyTrainTicket dbTicket) {
        List<ConfirmOrderTicketReq> tickets = req.getTickets();
        Integer i = null;
        Field field = null;
        for (ConfirmOrderTicketReq ticketReq : tickets) {
            String seatTypeCode = ticketReq.getSeatTypeCode();
            TrainSeatEnum seatTypeEnum = EnumUtil.getBy(TrainSeatEnum::getCode, seatTypeCode);
            StringBuffer sb = new StringBuffer(seatTypeEnum.name());
            String seatType = Strings.toLowerCase(sb.toString());
            try {
                field = DailyTrainTicket.class.getDeclaredField(seatType);
                field.setAccessible(true);
                i = (Integer) field.get(dbTicket);
                // 设置值
                i--;
                if (i < 0) {
                    throw new BizException(BizExceptionEnum.TICKETS_LIMITS);
                }
                field.set(dbTicket, i);
            } catch (NoSuchFieldException e) {
                throw new RuntimeException(e);
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
