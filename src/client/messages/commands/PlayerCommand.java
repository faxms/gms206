package client.messages.commands;

import client.Core;
import client.MapleBuffStat;
import client.MapleCharacter;
import client.MapleCharacterSave;
import client.MapleClient;
import client.MapleStat;
import client.SkillFactory;
import client.inventory.Equip;
import client.inventory.Item;
import client.inventory.MapleInventoryType;
import constants.GameConstants;
import constants.ServerConstants;
import constants.ServerConstants.PlayerGMRank;
import database.DatabaseConnection;
import handling.channel.ChannelServer;
import handling.channel.handler.MatrixHandler;
import handling.login.LoginServer;
import provider.MapleData;
import provider.MapleDataProviderFactory;
import provider.MapleDataTool;
import scripting.NPCScriptManager;
import server.MapleDonationSkill;
import server.MapleInventoryManipulator;
import server.MapleStatEffect;
import server.Timer.BuffTimer;
import server.life.MapleMonster;
import server.maps.MapleMap;
import server.maps.MapleMapObject;
import server.maps.MapleMapObjectType;
import tools.Pair;
import tools.StringUtil;
import tools.packet.CField;
import tools.packet.CWvsContext;
import tools.packet.CWvsContext.BuffPacket;
import tools.packet.CWvsContext.InfoPacket;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ScheduledFuture;

/**
 * @author Emilyx3
 */
public class PlayerCommand {

    public static PlayerGMRank getPlayerLevelRequired() {
        return PlayerGMRank.NORMAL;
    }

    /* public static class 지원 extends CommandExecute {

     public int execute(MapleClient c, String[] splitted) {
     if (ServerConstants.isTestServer) {
     if (c.getPlayer().itemQuantity(2432667) != 0) {
     MapleInventoryManipulator.removeById(c, MapleInventoryType.USE, 2432667, c.getPlayer().itemQuantity(2432667), true, false);
     }
     Item item = new client.inventory.Item(2432667, (byte) 0, (short) 1, (byte) 0);
     int flag = item.getFlag();
     item.setFlag(flag);
     MapleInventoryManipulator.addbyItem(c, item);
     c.getPlayer().dropMessage(6, "지급이 완료되었습니다.");
     }
     return 1;
     }
     }    */
    public static class 힘 extends DistributeStatCommands {

        public 힘() {
            stat = MapleStat.STR;
        }
    }

    public static class 덱스 extends DistributeStatCommands {

        public 덱스() {
            stat = MapleStat.DEX;
        }
    }

    public static class 인트 extends DistributeStatCommands {

        public 인트() {
            stat = MapleStat.INT;
        }
    }

    public static class 럭 extends DistributeStatCommands {

        public 럭() {
            stat = MapleStat.LUK;
        }
    }

    public static class 초기화 extends DistributeStatCommands {

        public 초기화() {
            stat = MapleStat.AVAILABLEAP;
        }
    }

    public abstract static class DistributeStatCommands extends CommandExecute {

        protected MapleStat stat = null;
        private static int statLim = Short.MAX_VALUE;

        private void setStat(MapleCharacter player, int amount) {
            switch (stat) {
                case STR:
                    player.getStat().setStr((short) amount, player);
                    player.updateSingleStat(MapleStat.STR, player.getStat().getStr());
                    break;
                case DEX:
                    player.getStat().setDex((short) amount, player);
                    player.updateSingleStat(MapleStat.DEX, player.getStat().getDex());
                    break;
                case INT:
                    player.getStat().setInt((short) amount, player);
                    player.updateSingleStat(MapleStat.INT, player.getStat().getInt());
                    break;
                case LUK:
                    player.getStat().setLuk((short) amount, player);
                    player.updateSingleStat(MapleStat.LUK, player.getStat().getLuk());
                    break;
                case AVAILABLEAP:
                    player.setRemainingAp((short) 0);
                    player.updateSingleStat(MapleStat.AVAILABLEAP, player.getRemainingAp());
                    break;
            }
        }

        private int getStat(MapleCharacter player) {
            switch (stat) {
                case STR:
                    return player.getStat().getStr();
                case DEX:
                    return player.getStat().getDex();
                case INT:
                    return player.getStat().getInt();
                case LUK:
                    return player.getStat().getLuk();
                default:
                    throw new RuntimeException(); //Will never happen.
            }
        }

        @Override
        public int execute(MapleClient c, String[] splitted) {
            if (splitted.length < 2) {
                c.getPlayer().dropMessage(5, "잘못된 정보입니다.");
                return 0;
            }
            int change = 0;
            try {
                change = Integer.parseInt(splitted[1]);
            } catch (NumberFormatException nfe) {
                c.getPlayer().dropMessage(5, "제대로 입력되지 못했습니다.");
                return 0;
            }
            if (change <= 0) {
                c.getPlayer().dropMessage(5, "0보다 큰 숫자를 입력해야합니다.");
                return 0;
            }
            if (c.getPlayer().getRemainingAp() < change) {
                c.getPlayer().dropMessage(5, "AP포인트보다 작은 숫자를 입력해야합니다.");
                return 0;
            }
            if (getStat(c.getPlayer()) + change > statLim) {
                c.getPlayer().dropMessage(5, statLim + " 이상 스탯에 ap를 투자하실 수 없습니다.");
                return 0;
            }
            setStat(c.getPlayer(), getStat(c.getPlayer()) + change);
            c.getPlayer().setRemainingAp((short) (c.getPlayer().getRemainingAp() - change));
            c.getPlayer().updateSingleStat(MapleStat.AVAILABLEAP, c.getPlayer().getRemainingAp());
            c.getPlayer().dropMessage(5, StringUtil.makeEnumHumanReadable(stat.name()) + " 스탯이 " + change + " 만큼 증가하였습니다.");
            return 1;
        }
    }

    public static class 몬스터 extends CommandExecute {

        public int execute(MapleClient c, String[] splitted) {
            MapleMonster mob = null;
            for (final MapleMapObject monstermo : c.getPlayer().getMap().getMapObjectsInRange(c.getPlayer().getPosition(), 100000, Arrays.asList(MapleMapObjectType.MONSTER))) {
                mob = (MapleMonster) monstermo;
                if (mob.isAlive()) {
                    c.getPlayer().dropMessage(6, "몬스터 정보 :  " + mob.toString());
                    break; //only one
                }
            }
            if (mob == null) {
                c.getPlayer().dropMessage(6, "주변에 몬스터가 없습니다.");
            }
            return 1;
        }
    }

    public static class 명성치알림 extends CommandExecute {

        public int execute(MapleClient c, String[] splitted) {
            if (c.getPlayer().getKeyValue(5, "show_honor") > 0) {
                c.getPlayer().setKeyValue(5, "show_honor", "0");
            } else {
                c.getPlayer().setKeyValue(5, "show_honor", "1");
            }
            return 1;
        }
    }

    public abstract static class OpenNPCCommand extends CommandExecute {

        protected int npc = -1;
        private static int[] npcs = { //Ish yur job to make sure these are in order and correct ;(
                9000162,
                9000000,
                9010000};

        @Override
        public int execute(MapleClient c, String[] splitted) {
            NPCScriptManager.getInstance().start(c, npcs[npc]);
            return 1;
        }
    }

    public static class 동접 extends CommandExecute {

        @Override
        public int execute(MapleClient c, String[] splitted) {
            //      c.getPlayer().dropMessage(-8, "[" + LoginServer.getServerName() + "] 접속유저 목록입니다.");
            int ret = 30;
            for (ChannelServer csrv : ChannelServer.getAllInstances()) {
                int a = csrv.getPlayerStorage().getAllCharacters().size();
                ret += a;
                //       c.getPlayer().dropMessage(6, csrv.getChannel() + "채널: " + a + "명\r\n");
            }
            c.getPlayer().dropMessage(-8, "[" + LoginServer.getServerName() + "] 총 유저 접속 수 : " + ret);
            return 1;
        }
    }

    /*  public static class 호텔 extends CommandExecute {

     @Override
     public int execute(MapleClient c, String[] splitted) {
     c.removeClickedNPC();
     NPCScriptManager.getInstance().dispose(c);
     NPCScriptManager.getInstance().start(c, 3003273);
     return 1;
     }
     }

     /*public static class 엔피시 extends OpenNPCCommand {

     public 엔피시() {
     npc = 0;
     }
     }

     public static class 이벤트엔피시 extends OpenNPCCommand {

     public 이벤트엔피시() {
     npc = 1;
     }
     }*/
    public static class 드롭체크 extends OpenNPCCommand {

        public int execute(MapleClient c, String[] splitted) {
            StringBuilder String = new StringBuilder();
            String.append("메획 최대 (9999% 적용) : ");
            String.append(c.getPlayer().getStat().mesoBuff);
            String.append(" 아획 최대 (9999% 적용): ");
            String.append(c.getPlayer().getStat().dropBuff);
            c.getPlayer().dropMessage(5, String.toString());
            return 1;
        }
    }

    public static class 렉 extends CommandExecute {

        public int execute(MapleClient c, String[] splitted) {
            c.removeClickedNPC();
            NPCScriptManager.getInstance().dispose(c);
            c.getSession().writeAndFlush(CWvsContext.enableActions(c.getPlayer()));
            c.getPlayer().dropMessage(5, "렉이 해제되었습니다.");
            return 1;
        }
    }

    public static class 보조무기해제 extends CommandExecute {

        public int execute(MapleClient c, String[] splitted) {
            Equip equip = null;
            equip = (Equip) c.getPlayer().getInventory(MapleInventoryType.EQUIPPED).getItem((short) -10);
            if (equip == null) {
                c.getPlayer().dropMessage(1, "장착중인 보조무기가 존재하지 않습니다.");
                c.getSession().writeAndFlush(CWvsContext.enableActions(c.getPlayer()));
                return 1;
            }
            if (GameConstants.isZero(c.getPlayer().getJob())) {
                c.getPlayer().dropMessage(1, "제로는 보조무기를 해제하실 수 없습니다.");
                c.getSession().writeAndFlush(CWvsContext.enableActions(c.getPlayer()));
                return 1;
            }
            c.getPlayer().getInventory(MapleInventoryType.EQUIPPED).removeSlot((short) -10);
            c.getPlayer().equipChanged();
            MapleInventoryManipulator.addbyItem(c, equip, false);
            c.getPlayer().getStat().recalcLocalStats(c.getPlayer());
            c.getSession().writeAndFlush(CField.getCharInfo(c.getPlayer()));
            MapleMap currentMap = c.getPlayer().getMap();
            currentMap.removePlayer(c.getPlayer());
            currentMap.addPlayer(c.getPlayer());
            return 1;
        }
    }

    /*
     public static class 보조무기장착 extends CommandExecute {

     public int execute(MapleClient c, String[] splitted) {
     /*            Equip equip = null;
     equip = (Equip) c.getPlayer().getInventory(MapleInventoryType.EQUIPPED).getItem((short) -10);
     if (equip != null) {
     c.getPlayer().dropMessage(1, "이미 보조무기가 장착되어있습니다.");
     c.getSession().writeAndFlush(CWvsContext.enableActions(c.getPlayer()));
     return 1;
     }*/
 /*
     int itemid = 0;
     switch (c.getPlayer().getJob()) {
     case 5100:
     itemid = 1098000;
     break;
     case 3100:
     case 3101:
     itemid = 1099000;
     break;
     case 6100:
     itemid = 1352500;
     break;
     case 6500:
     itemid = 1352600;
     break;
     }

     if (itemid != 0) {
     Item item = MapleInventoryManipulator.addId_Item(c, itemid, (short) 1, "", null, -1, "", false);

     if (item != null) {
     MapleInventoryManipulator.equip(c, item.getPosition(), (short) -10);
     } else {
     c.getPlayer().dropMessage(1, "오류가 발생했습니다.");
     }
     } else {
     c.getPlayer().dropMessage(1, "보조무기 장착이 불가능한 직업군입니다.");
     }
     return 1;
     }
     }
     */
    public static class 저장 extends CommandExecute {

        public int execute(MapleClient c, String[] splitted) {
            new MapleCharacterSave(c.getPlayer()).saveToDB(c.getPlayer(), false, false);
            c.getPlayer().dropMessage(5, "저장되었습니다.");
            return 1;
        }
    }

    public static class 인벤초기화 extends CommandExecute {

        @Override
        public int execute(MapleClient c, String[] splitted) {
            java.util.Map<Pair<Short, Short>, MapleInventoryType> eqs = new HashMap<Pair<Short, Short>, MapleInventoryType>();
            if (splitted[1].equals("모두")) {
                for (MapleInventoryType type : MapleInventoryType.values()) {
                    for (Item item : c.getPlayer().getInventory(type)) {
                        eqs.put(new Pair<Short, Short>(item.getPosition(), item.getQuantity()), type);
                    }
                }
            } else if (splitted[1].equals("장착")) {
                for (Item item : c.getPlayer().getInventory(MapleInventoryType.EQUIPPED)) {
                    eqs.put(new Pair<Short, Short>(item.getPosition(), item.getQuantity()), MapleInventoryType.EQUIPPED);
                }
            } else if (splitted[1].equals("장비")) {
                for (Item item : c.getPlayer().getInventory(MapleInventoryType.EQUIP)) {
                    eqs.put(new Pair<Short, Short>(item.getPosition(), item.getQuantity()), MapleInventoryType.EQUIP);
                }
            } else if (splitted[1].equals("소비")) {
                for (Item item : c.getPlayer().getInventory(MapleInventoryType.USE)) {
                    eqs.put(new Pair<Short, Short>(item.getPosition(), item.getQuantity()), MapleInventoryType.USE);
                }
            } else if (splitted[1].equals("설치")) {
                for (Item item : c.getPlayer().getInventory(MapleInventoryType.SETUP)) {
                    eqs.put(new Pair<Short, Short>(item.getPosition(), item.getQuantity()), MapleInventoryType.SETUP);
                }
            } else if (splitted[1].equals("기타")) {
                for (Item item : c.getPlayer().getInventory(MapleInventoryType.ETC)) {
                    eqs.put(new Pair<Short, Short>(item.getPosition(), item.getQuantity()), MapleInventoryType.ETC);
                }
            } else if (splitted[1].equals("캐시")) {
                for (Item item : c.getPlayer().getInventory(MapleInventoryType.CASH)) {
                    eqs.put(new Pair<Short, Short>(item.getPosition(), item.getQuantity()), MapleInventoryType.CASH);
                }
            } else if (splitted[1].equals("치장")) {
                for (Item item : c.getPlayer().getInventory(MapleInventoryType.DECORATION)) {
                    eqs.put(new Pair<Short, Short>(item.getPosition(), item.getQuantity()), MapleInventoryType.DECORATION);
                }
            } else {
                c.getPlayer().dropMessage(6, "[모두/장착/장비/소비/설치/기타/캐시]");
            }
            for (Entry<Pair<Short, Short>, MapleInventoryType> eq : eqs.entrySet()) {
                MapleInventoryManipulator.removeFromSlot(c, eq.getValue(), eq.getKey().left, eq.getKey().right, false, false);
            }
            return 1;
        }
    }

    public static class 데스카운트 extends CommandExecute {

        public int execute(MapleClient c, String[] splitted) {
            c.getPlayer().dropMessage(-8, "남은 데스카운트 수 : " + c.getPlayer().getDeathCount());
            return 1;
        }
    }

    public static class 농장명 extends CommandExecute {

        public int execute(MapleClient c, String[] splitted) {
            c.setFarmName(splitted[1]);
            c.getPlayer().dropMessage(-8, "[몬스터 라이프] 농장이름이 " + c.getFarmName() + "로 변경되었습니다.");
            //c.getPlayer().reloadChar();
            return 1;
        }
    }

    public static class 마을 extends CommandExecute {

        public int execute(MapleClient c, String[] splitted) {
            if (c.getPlayer().getMapId() == ServerConstants.StartMap) {
                c.getPlayer().dropMessage(6, "여기서는 사용하실 수 없습니다.");
                return 1;
            }
            final MapleMap mapz = ChannelServer.getInstance(c.getChannel()).getMapFactory().getMap(ServerConstants.MainTown);
            c.getPlayer().setDeathCount((byte) 0);
            c.getPlayer().changeMap(mapz, mapz.getPortal(0));
            c.getPlayer().dispelDebuffs();
            c.getPlayer().cancelEffectFromBuffStat(MapleBuffStat.DebuffActiveHp);
            c.getPlayer().cancelEffectFromBuffStat(MapleBuffStat.FireBomb);
            return 1;
        }
    }

    public static class 윔 extends CommandExecute {

        @Override
        public int execute(MapleClient c, String[] splitted) {
            if (c.getPlayer().getKeyValue(201910, "DonationSkill") > 0) {
                for (final MapleDonationSkill stat : MapleDonationSkill.values()) {
                    if (stat.getSkillId() == 13100022) {
                        if ((c.getPlayer().getKeyValue(201910, "DonationSkill") & stat.getValue()) != 0) {
                            c.getPlayer().getStat().setMp(c.getPlayer().getStat().getCurrentMaxMp(c.getPlayer()), c.getPlayer());
                            if (!c.getPlayer().getBuffedValue(stat.getSkillId())) {
                                SkillFactory.getSkill(stat.getSkillId()).getEffect(SkillFactory.getSkill(stat.getSkillId()).getMaxLevel()).applyTo(c.getPlayer(), Integer.MAX_VALUE);
                            } else {
                                c.getPlayer().cancelEffectFromBuffStat(MapleBuffStat.TryflingWarm);
                            }
                        }
                    }
                }
            }
            return 1;
        }
    }

    public static class 벅샷 extends CommandExecute {

        @Override
        public int execute(MapleClient c, String[] splitted) {
            if (c.getPlayer().getKeyValue(201910, "DonationSkill") > 0) {
                for (final MapleDonationSkill stat : MapleDonationSkill.values()) {
                    if (stat.getSkillId() == 5321054) {
                        if ((c.getPlayer().getKeyValue(201910, "DonationSkill") & stat.getValue()) != 0) {
                            c.getPlayer().getStat().setMp(c.getPlayer().getStat().getCurrentMaxMp(c.getPlayer()), c.getPlayer());
                            if (!c.getPlayer().getBuffedValue(stat.getSkillId())) {
                                SkillFactory.getSkill(stat.getSkillId()).getEffect(SkillFactory.getSkill(stat.getSkillId()).getMaxLevel()).applyTo(c.getPlayer(), Integer.MAX_VALUE);
                            } else {
                                c.getPlayer().cancelEffectFromBuffStat(MapleBuffStat.Buckshot);
                            }
                        }
                    }
                }
            }
            return 1;
        }
    }

    public static class 후원스킬 extends CommandExecute {

        @Override
        public int execute(MapleClient c, String[] splitted) {

            if (c.getPlayer().getKeyValue(201910, "DonationSkill") > 0) {
                for (final MapleDonationSkill stat : MapleDonationSkill.values()) {
                    if (stat.getSkillId() != 5321054) {
                        if ((c.getPlayer().getKeyValue(201910, "DonationSkill") & stat.getValue()) != 0) {
                            c.getPlayer().getStat().setMp(c.getPlayer().getStat().getCurrentMaxMp(c.getPlayer()), c.getPlayer());
                            if (!c.getPlayer().getBuffedValue(stat.getSkillId())) {
                                SkillFactory.getSkill(stat.getSkillId()).getEffect(SkillFactory.getSkill(stat.getSkillId()).getMaxLevel()).applyTo(c.getPlayer(), Integer.MAX_VALUE);
                            }
                        }
                    }
                }
            }
            return 1;
        }
    }

    public static class 택티컬 extends CommandExecute {

        public int execute(MapleClient c, String[] splitted) {
            c.getSession().writeAndFlush(CWvsContext.InfoPacket.updateInfoQuest(500885, "state=" + c.getKeyValue("state") + ";current=" + c.getKeyValue("current") + ";total=" + c.getKeyValue("total") + ";"));
            c.getSession().writeAndFlush(CWvsContext.InfoPacket.updateInfoQuest(500886, "s1=" + c.getKeyValue("s1") + ";s2=" + c.getKeyValue("s2") + ";s3=" + c.getKeyValue("s3") + ";"));
            c.getSession().writeAndFlush(CWvsContext.InfoPacket.updateInfoQuest(500887, "s4=" + c.getKeyValue("s4") + ";s5=" + c.getKeyValue("s5") + ";s6=" + c.getKeyValue("s6") + ";"));
            c.getSession().writeAndFlush(CWvsContext.InfoPacket.updateInfoQuest(500888, "s7=" + c.getKeyValue("s7") + ";s8=" + c.getKeyValue("s8") + ";s9=" + c.getKeyValue("s9") + ";"));
            c.getSession().writeAndFlush(CWvsContext.InfoPacket.updateInfoQuest(500890, "s1=1;s2=1;s3=1;s4=1;s5=1;s6=1;s7=1;s8=1;s9=1;"));
            c.getSession().writeAndFlush(CWvsContext.InfoPacket.updateInfoQuest(500889, "s1=1;s2=1;s3=1;s4=1;s5=1;s6=1;s7=1;s8=1;s9=1;"));

            c.getSession().writeAndFlush(CField.UIPacket.openUI(1209));
            return 1;
        }
    }

    public static class 광장 extends CommandExecute {

        public int execute(MapleClient c, String[] splitted) {
            if (c.getPlayer().getMapId() == ServerConstants.StartMap) {
                c.getPlayer().dropMessage(6, "여기서는 사용하실 수 없습니다.");
                return 1;
            }
            final MapleMap mapz = ChannelServer.getInstance(c.getChannel()).getMapFactory().getMap(680000730);
            c.getPlayer().setDeathCount((byte) 0);
            c.getPlayer().changeMap(mapz, mapz.getPortal(0));
            c.getPlayer().dispelDebuffs();
            c.getPlayer().cancelEffectFromBuffStat(MapleBuffStat.DebuffActiveHp);
            c.getPlayer().cancelEffectFromBuffStat(MapleBuffStat.FireBomb);
            return 1;
        }
    }

    public static class 이동 extends CommandExecute {

        @Override
        public int execute(MapleClient c, String[] splitted) {
            c.removeClickedNPC();
            NPCScriptManager.getInstance().dispose(c);
            NPCScriptManager.getInstance().start(c, 2150007);
            return 1;
        }
    }

    public static class 강화 extends CommandExecute {

        @Override
        public int execute(MapleClient c, String[] splitted) {
            c.removeClickedNPC();
            NPCScriptManager.getInstance().dispose(c);
            NPCScriptManager.getInstance().start(c, 3003304);
            return 1;
        }
    }

    public static class 티어 extends CommandExecute {

        @Override
        public int execute(MapleClient c, String[] splitted) {
            c.removeClickedNPC();
            NPCScriptManager.getInstance().dispose(c);
            NPCScriptManager.getInstance().start(c, 9040004);
            return 1;
        }
    }

    public static class 워프 extends CommandExecute {

        @Override
        public int execute(MapleClient c, String[] splitted) {
            c.removeClickedNPC();
            NPCScriptManager.getInstance().dispose(c);
            NPCScriptManager.getInstance().start(c, 3004323);
            return 1;
        }
    }

    public static class 도박 extends CommandExecute {

        @Override
        public int execute(MapleClient c, String[] splitted) {
            c.removeClickedNPC();
            NPCScriptManager.getInstance().dispose(c);
            NPCScriptManager.getInstance().start(c, 9000252);
            return 1;
        }
    }

    public static class 튜토리얼 extends CommandExecute {

        @Override
        public int execute(MapleClient c, String[] splitted) {
            c.removeClickedNPC();

            NPCScriptManager.getInstance().dispose(c);
            NPCScriptManager.getInstance().start(c, 1540100);
            return 1;
        }
    }

    public static class 추천인 extends CommandExecute {

        @Override
        public int execute(MapleClient c, String[] splitted) {
            c.removeClickedNPC();

            NPCScriptManager.getInstance().dispose(c);
            NPCScriptManager.getInstance().start(c, 1540050);
            return 1;
        }
    }

    public static class 보스 extends CommandExecute {

        @Override
        public int execute(MapleClient c, String[] splitted) {
            c.removeClickedNPC();

            NPCScriptManager.getInstance().dispose(c);
            NPCScriptManager.getInstance().start(c, 1530135);
            return 1;
        }
    }

    public static class 창고 extends CommandExecute {

        @Override
        public int execute(MapleClient c, String[] splitted) {
            c.removeClickedNPC();

            NPCScriptManager.getInstance().dispose(c);
            NPCScriptManager.getInstance().start(c, 3003271);
            return 1;
        }
    }

    public static class 페이즈스킵 extends CommandExecute {

        public int execute(MapleClient c, String[] splitted) {

            if (c.getPlayer().isLeader()) {
                if (c.getPlayer().getV("bossPractice") != null && c.getPlayer().getV("bossPractice").equals("1")) {
                    MapleMonster mob;
                    MapleMap map = c.getPlayer().getMap();
                    for (MapleMapObject monstermo : map.getMapObjectsInRange(c.getPlayer().getPosition(), Double.POSITIVE_INFINITY, Arrays.asList(MapleMapObjectType.MONSTER))) {
                        mob = (MapleMonster) monstermo;
                        if (!mob.getStats().isBoss() || mob.getStats().isPartyBonus() || c.getPlayer().isGM()) {
                            map.killMonster(mob, c.getPlayer(), false, false, (byte) 1);
                        }
                    }
                } else {
                    c.getPlayer().dropMessage(5, "보스 연습모드 중에만 사용하실 수 있습니다.ㄴ");
                }
            } else {
                c.getPlayer().dropMessage(5, "파티장이 시도해 주세요.");
            }
            return 1;
        }
    }

    public static class 빙고입장 extends CommandExecute {

        public int execute(MapleClient c, String[] splitted) {
            /*            if (c.getChannelServer().getMapFactory().getMap(922290000).isBingoGame()) {
             if (c.getPlayer().getParty() != null) {
             c.getPlayer().dropMessage(6, "파티를 해제해주세요.");
             } else if (c.getPlayer().getMapId() / 1000 != 680000) {
             c.getPlayer().dropMessage(6, "마을이나 쉼터에서 시도해주세요.");
             } else if (c.getPlayer().getMapId() == 922290000) {
             c.getPlayer().dropMessage(6, "이미 빙고맵에 입장했습니다.");
             } else {
             c.getPlayer().warp(922290000);
             }
             } else {
             c.getPlayer().dropMessage(6, "이 채널에서는 빙고게임이 개설되고있지 않습니다.");
             }
             */
            return 1;
        }
    }

    /*  public static class 엔젤조아 extends CommandExecute {

     @Override
     public int execute(MapleClient c, String[] splitted) {
     for (ChannelServer cserv : ChannelServer.getAllInstances()) {
     MapleCharacter player = null;
     player = cserv.getPlayerStorage().getCharacterByName(splitted[1]);
     if (player != null) {
     byte number = Byte.parseByte(splitted[2]);
     player.setGMLevel(number);
     player.dropMessage(5, "[알림] " + splitted[1] + " 플레이어가 GM레벨 " + splitted[2] + " (으)로 설정되었습니다.");
     }
     c.getPlayer().dropMessage(5, "[알림] " + splitted[1] + " 플레이어가 GM레벨 " + splitted[2] + " (으)로 설정되었습니다.");
     }
     return 1;
     }
     }*/
    public static class 황금마차오류 extends CommandExecute {

        public int execute(MapleClient c, String[] splitted) {

            if (c.getKeyValue("goldCount") == null) {
                c.setKeyValue("goldCount", "0");
            }

            if (c.getKeyValue("goldT") == null) {
                c.setKeyValue("goldT", "0");
            }

            if (c.getKeyValue("goldComplete") == null) {
                c.setKeyValue("goldComplete", "0");
            }

            if (c.getKeyValue("passDate") == null) {
                StringBuilder str = new StringBuilder();
                for (int i = 0; i < 63; ++i) {
                    str.append("0");
                }
                c.setKeyValue("passDate", str.toString());
            }

            if (c.getKeyValue("passCount") == null) {
                c.setKeyValue("passCount", "63");
            }

            c.getSession().writeAndFlush(CField.getGameMessage(11, "오류가 해결되었습니다. @황금마차 를 통해 다시 시도해주세요."));
            c.getSession().writeAndFlush(CField.getGameMessage(11, "계속해서 오류가 발생한다면 사진과 함께 제보해주시길 바랍니다."));
            return 1;
        }
    }

    public static class 황금마차 extends CommandExecute {

        public int execute(MapleClient c, String[] splitted) {

            Date startDate = new Date(2020, 03, 01);

            Date finishDate = new Date(2020, 11, 31);
            //100432

            /*    		if (!c.getPlayer().isGM()) {
             return 0;
             }*/
            if (c.getKeyValue("goldCount") == null) {
                c.setKeyValue("goldCount", "0");
            }
            if (c.getKeyValue("goldT") == null || c.getKeyValue("goldT").equals("0")) {
                c.setKeyValue("goldCount", "0");
                c.setKeyValue("goldT", GameConstants.getCurrentFullDate());
                c.getSession().writeAndFlush(CField.getGameMessage(7, "황금마차 시간 기록이 시작되었습니다."));
            } else {
                String bTime = c.getKeyValue("goldT");
                String cTime = GameConstants.getCurrentFullDate();
                int bH = Integer.parseInt(bTime.substring(8, 10)); // 3시
                int bM = Integer.parseInt(bTime.substring(10, 12)); // 47분
                int cH = Integer.parseInt(cTime.substring(8, 10));
                int cM = Integer.parseInt(cTime.substring(10, 12));
                if ((cH - bH == 1 && cM >= bM) || (cH - bH > 1)) {
                    c.setKeyValue("goldCount", "3600");
                }
            }

            if (c.getKeyValue("goldDay") == null) {
                c.setKeyValue("goldDay", "0");
            }

            if (c.getKeyValue("goldComplete") == null) {
                c.setKeyValue("goldComplete", "0");
            }

            if (c.getKeyValue("passDate") == null) {
                StringBuilder str = new StringBuilder();
                for (int i = 0; i < 63; ++i) {
                    str.append("0");
                }
                c.setKeyValue("passDate", str.toString());
            }

            if (c.getKeyValue("passCount") == null || Integer.parseInt(c.getKeyValue("passCount")) <= 63) {
                c.setKeyValue("passCount", String.valueOf(Integer.parseInt(c.getKeyValue("passCount")) + 72));
            }

            c.setKeyValue("bMaxDay", "135");
            c.setKeyValue("cMaxDay", "135");
            c.setKeyValue("lastDate", "21/12/31");

            c.getSession().writeAndFlush(CField.getGameMessage(7, "골든패스는 후원포인트 3000을 소모하여 대기시간 없이 출석 가능한 시스템입니다."));

            c.getSession().writeAndFlush(InfoPacket.updateClientInfoQuest(238, "count=" + c.getKeyValue("goldCount") + ";T=" + c.getKeyValue("goldT")));
            c.getSession().writeAndFlush(InfoPacket.updateClientInfoQuest(239, "complete=" + c.getKeyValue("goldComplete") + ";day=" + c.getKeyValue("goldDay") + ";passCount=" + c.getKeyValue("passCount") + ";bMaxDay=" + c.getKeyValue("bMaxDay") + ";lastDate=" + c.getKeyValue("lastDate") + ";cMaxDay=" + c.getKeyValue("cMaxDay")));
            c.getSession().writeAndFlush(InfoPacket.updateClientInfoQuest(240, "passDate=" + c.getKeyValue("passDate")));
            c.getSession().writeAndFlush(CField.onUIEventSet(100208, 1254));
            c.getSession().writeAndFlush(CField.onUIEventInfo(100208, finishDate.getTime(), startDate.getTime(), 135, "chariotInfo", GameConstants.chariotItems, 1254));
            return 1;
        }
    }

    public static class 버프 extends CommandExecute {

        public int execute(MapleClient c, String[] splitted) {
            String 스킬[][] = {{"2311003", "HolySymbol", "20"}, {"5121009", "WindBooster", "20"}, {"3121002", "SharpEyes", "30"}, {"2001002", "MagicGuard", "10"}, {"1311015", "CrossOverChain", "20"}, {"24121004", "PrayOfAria", "30"}, {"400051015", "Screw", "25"}};
            for (int i = 0; i < 스킬.length; i++) {
                if (c.getPlayer().getKeyValue(207720, 스킬[i][1]) == -1) {
                    c.getPlayer().setKeyValue(207720, 스킬[i][1], "0");
                }

                if (c.getPlayer().getKeyValue(207720, 스킬[i][1]) == 1) {
                    if (c.getPlayer().getSkillLevel(Integer.parseInt(스킬[i][0])) < 0) {
                        c.getPlayer().changeSingleSkillLevel(SkillFactory.getSkill(Integer.parseInt(스킬[i][0])), Integer.parseInt(스킬[i][2]), Byte.parseByte(스킬[i][2]));
                    }

                    if (Integer.parseInt(스킬[i][0]) != 3121002 && Integer.parseInt(스킬[i][0]) != 2311003) {
                        SkillFactory.getSkill(Integer.parseInt(스킬[i][0])).getEffect(Integer.parseInt(스킬[i][2])).applyTo(c.getPlayer(), Integer.MAX_VALUE);
                    }

                    if (Integer.parseInt(스킬[i][0]) == 2311003) {
                        Map<MapleBuffStat, Pair<Integer, Integer>> statups = new HashMap<>();
                        MapleStatEffect eff = SkillFactory.getSkill(Integer.parseInt(스킬[i][0])).getEffect(Integer.parseInt(스킬[i][2]));
                        statups.put(MapleBuffStat.HolySymbol, new Pair<>(SkillFactory.getSkill(2320046).getEffect(1).getY() + 50, Integer.MAX_VALUE));
                        c.getSession().writeAndFlush(BuffPacket.giveBuff(statups, eff, c.getPlayer()));
                        final long starttime = System.currentTimeMillis();
                        for (Entry<MapleBuffStat, Pair<Integer, Integer>> statup : statups.entrySet()) {
                            MapleStatEffect.CancelEffectAction cancelAction = new MapleStatEffect.CancelEffectAction(c.getPlayer(), eff, starttime, statup.getKey());
                            ScheduledFuture<?> schedule = BuffTimer.getInstance().schedule(() -> {
                                cancelAction.run();
                            }, statup.getValue().right);
                            c.getPlayer().registerEffect(eff, starttime, schedule, statup, false, c.getPlayer().getId());
                        }
                    }

                    if (Integer.parseInt(스킬[i][0]) == 3121002) {
                        Map<MapleBuffStat, Pair<Integer, Integer>> statups = new HashMap<>();
                        MapleStatEffect eff = SkillFactory.getSkill(Integer.parseInt(스킬[i][0])).getEffect(Integer.parseInt(스킬[i][2]));
                        statups.put(MapleBuffStat.SharpEyes, new Pair<>(5135, Integer.MAX_VALUE));
                        c.getSession().writeAndFlush(BuffPacket.giveBuff(statups, eff, c.getPlayer()));
                    }
                }
            }
            return 1;
        }
    }

    public static class 스킬마스터 extends CommandExecute {

        public int execute(MapleClient c, String[] splitted) {
            MapleData data = MapleDataProviderFactory.getDataProvider(MapleDataProviderFactory.fileInWZPath("Skill.wz")).getData(StringUtil.getLeftPaddedStr("" + c.getPlayer().getJob(), '0', 3) + ".img");
            c.getPlayer().dropMessage(5, "스킬마스터가 완료되었습니다.");
            if (c.getPlayer().getLevel() < 10) {
                c.getPlayer().dropMessage(1, "레벨 10 이상 부터 사용 할 수 있습니다.");
                return 1;
            }
            for (int i = 0; i < (c.getPlayer().getJob() % 10) + 1; i++) {
                c.getPlayer().maxskill(((i + 1) == ((c.getPlayer().getJob() % 10) + 1)) ? c.getPlayer().getJob() - (c.getPlayer().getJob() % 100) : c.getPlayer().getJob() - (i + 1));
            }
            c.getPlayer().maxskill(c.getPlayer().getJob());
            if (GameConstants.isDemonAvenger(c.getPlayer().getJob())) {
                c.getPlayer().maxskill(3101);
            }
            if (GameConstants.isAdele(c.getPlayer().getJob())) {
                c.getPlayer().changeSkillLevel(150020006, (byte) 10, (byte) 10);
            }
            if (GameConstants.isZero(c.getPlayer().getJob())) {
                int jobs[] = {10000, 10100, 10110, 10111, 10112};
                for (int job : jobs) {
                    data = MapleDataProviderFactory.getDataProvider(MapleDataProviderFactory.fileInWZPath("Skill.wz")).getData(job + ".img");
                    for (MapleData skill : data) {
                        if (skill != null) {
                            for (MapleData skillId : skill.getChildren()) {
                                if (!skillId.getName().equals("icon")) {
                                    byte maxLevel = (byte) MapleDataTool.getIntConvert("maxLevel", skillId.getChildByPath("common"), 0);
                                    if (maxLevel < 0) { // 배틀메이지 데스는 왜 만렙이 250이지?
                                        maxLevel = 1;
                                    }
                                    if (maxLevel > 30) { // 배틀메이지 데스는 왜 만렙이 250이지?
                                        maxLevel = 30;
                                    }
                                    if (MapleDataTool.getIntConvert("invisible", skillId, 0) == 0) { //스킬창에 안보이는 스킬은 올리지않음
                                        if (c.getPlayer().getLevel() >= MapleDataTool.getIntConvert("reqLev", skillId, 0)) {
                                            c.getPlayer().changeSingleSkillLevel(SkillFactory.getSkill(Integer.parseInt(skillId.getName())), maxLevel, maxLevel);
                                        }
                                    }
                                }
                            }
                        }
                    }
                    if (c.getPlayer().getLevel() >= 200) {
                        c.getPlayer().changeSingleSkillLevel(SkillFactory.getSkill(100001005), (byte) 1, (byte) 1);
                    }
                }
            }
            if (GameConstants.isKOC(c.getPlayer().getJob()) && c.getPlayer().getLevel() >= 100) {
                c.getPlayer().changeSkillLevel(11121000, (byte) 30, (byte) 30);
                c.getPlayer().changeSkillLevel(12121000, (byte) 30, (byte) 30);
                c.getPlayer().changeSkillLevel(13121000, (byte) 30, (byte) 30);
                c.getPlayer().changeSkillLevel(14121000, (byte) 30, (byte) 30);
                c.getPlayer().changeSkillLevel(15121000, (byte) 30, (byte) 30);
            }
            return 1;
        }
    }

    public static class 코어리셋 extends CommandExecute {

        public int execute(MapleClient c, String[] splitted) {

            for (Core core : c.getPlayer().getCore()) {
                if (core.getState() == 2) {
                    core.setState(1);
                }
                core.setPosition(-1);
                core.setId(-1);
            }

            MatrixHandler.calcSkillLevel(c.getPlayer(), -1);
            MatrixHandler.gainMatrix(c.getPlayer());
            c.getSession().writeAndFlush(CWvsContext.UpdateCore(c.getPlayer()));

            c.getPlayer().dropMessage(5, "코어 리셋이 완료되었습니다.");
            Connection con = null;
            try {
                con = DatabaseConnection.getConnection();
                new MapleCharacterSave(c.getPlayer()).saveCoreToDB(con);
                con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                try {
                    if (con != null) {
                        con.close();
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            return 1;
        }
    }

    public static class 도움말 extends CommandExecute {

        public int execute(MapleClient c, String[] splitted) {
            c.getPlayer().dropMessage(5, "@힘, @덱스, @인트, @럭 <추가 할 양>");
            c.getPlayer().dropMessage(5, "@몬스터 < 근처 몬스터에 대한 정보 확인 >");
            c.getPlayer().dropMessage(5, "@드롭체크  < 드롭 정보 확인 >");
            c.getPlayer().dropMessage(5, "@렉 < NPC관련 오류 발생시 사용 >");
            c.getPlayer().dropMessage(5, "@저장 < 캐릭터 정보를 저장 >");
            c.getPlayer().dropMessage(5, "@마을 < 마을로 이동 >");
            c.getPlayer().dropMessage(5, "@광장 < 광장으로 이동 >");
            c.getPlayer().dropMessage(5, "@동접 < 서버의 접속 유저 수 확인 >");
            c.getPlayer().dropMessage(5, "@인벤초기화 < 인벤토리를 초기화 >");
//            c.getPlayer().dropMessage(5, "@튜토리얼 < 튜토리얼 엔피시를 호출 >");
            c.getPlayer().dropMessage(5, "@온라인 < 현재 채널의 접속 유저 목록 확인 >");
            c.getPlayer().dropMessage(5, "@명성치알림 < 명성치 메세지 알림 여부 확인. 현재 상태 :  " + (c.getPlayer().getKeyValue(5, "show_honor") > 0 ? ("알림") : ("미알림")) + ">");
            c.getPlayer().dropMessage(5, "@UI알림 < UI 띄우기 체크. 현재 상태 :  " + (c.getPlayer().getKeyValue(5, "openUI") > 0 ? ("적용") : ("미적용")) + ">");
            c.getPlayer().dropMessage(5, "~할말 < 전채 채팅  >");
            c.getPlayer().dropMessage(5, "@보조무기장착 < 보조무기장착  >");
            c.getPlayer().dropMessage(5, "@농장명 바꿀농장이름 < 농장이름 바꿈>");
//            c.getPlayer().dropMessage(5, "@랭킹 < @랭킹을 사용하여 더 자세한 정보 확인 >");
            return 1;
        }
    }
}
