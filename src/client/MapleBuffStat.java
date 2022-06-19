package client;

import constants.GameConstants;
import handling.Buffstat;
import server.Randomizer;
import tools.Pair;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public enum MapleBuffStat implements Serializable, Buffstat {

    IndiePad(0),
    IndieMad(1),
    IndiePdd(2),
    IndieHp(3),
    IndieHpR(4), // ok
    IndieMp(5),
    IndieMpR(6), // ok
    IndieAcc(7), // dummy
    IndieEva(8), // dummy
    IndieJump(9),
    IndieSpeed(10),
    IndieAllStat(11), // ok
    IndieAllStatR(12), // ok
    IndieDodgeCriticalTime(13),
    IndieExp(14),
    IndieBooster(15),
    IndieFixedDamageR(16),
    PyramidStunBuff(17),
    PyramidFrozenBuff(18),
    PyramidFireBuff(19),
    PyramidBonusDamageBuff(20), // ok
    IndieRelaxEXP(21),
    IndieStr(22), // ok
    IndieDex(23), // ok
    IndieInt(24), // ok
    IndieLuk(25), // ok
    IndieDamR(26),
    IndieScriptBuff(27),
    IndieMaxDamageR(28),
    IndieAsrR(29),
    IndieTerR(30),
    IndieCr(31),
    IndiePddR(32), // no ??
    IndieCD(33),
    IndieBDR(34),
    IndieStatR(35),
    IndieStance(36), // ok
    IndieIgnoreMobPdpR(37),
    IndieEmpty(38),
    IndiePadR(39), // ok
    IndieMadR(40), // ok
    IndieEvaR(41),
    IndieDrainHP(42),
    IndiePmdR(43),
    IndieForceJump(44),
    IndieForceSpeed(45),
    IndieDamageReduce(46),
    IndieSummon(47),
    IndieReduceCooltime(48), // ok
    IndieNotDamaged(49), // ok
    IndieJointAttack(50), // 1.2.287
    IndieKeyDownMoving(51),
    IndieUnkIllium(52),
    IndieEvasion(53),
    IndieShotDamage(54),
    IndieSuperStance(55), // 1.2.329
    IndieGrandCross(56),
    IndieBarrierDischarge(57), // 1.2.329
    Indie_STAT_COUNT(58),
    Indie_DamageReflection(59), //히어로 뎀반사
    Unk60(60),
    Unk61(61),
    IndieFloating(62), // 1.2.329
    IndieWickening(63), //  334 맞추는중
    IndieWickening1(64), //  324
    IndieWickening2(65), //  324
    IndieBlockSkill(66),
    IndieDarknessAura(67),
    Pad(73), //342 +1 시작
    Pdd(74),
    Mad(75),
    Acc(76),
    Eva(77),
    Craft(78), //1.2.1029
    Speed(79),
    Jump(80),
    MagicGaurd(81),
    DarkSight(82),
    Booster(83), //335 ?테스트ok79
    PowerGaurd(84),
    MaxHP(85),
    MaxMP(86),
    Invincible(87),
    SoulArrow(88),
    Stun(89), // 320 IDA
    Poison(90), // 320 IDA
    Seal(91), // 320 IDA
    Darkness(92), // 320 IDA
    ComboCounter(93), // 320 IDA
    WeaponCharge(94), // 320 IDA
    BlessedHammer(95), // 320 IDA
    BlessedHammer2(96), // ok
    SnowCharge(97),
    HolySymbol(98), // 320 IDA
    MesoUp(99),
    ShadowPartner(100), // 320 IDA
    PickPocket(101),
    MesoGuard(102),
    THAW(103),
    Weakness(104), // 320 IDA
    Curse(105), // 320 IDA
    Slow(106), // 320 IDA
    Morph(107), // 320 IDA
    Recovery(108),
    BasicStatUp(109), //334 ok  시작
    Stance(110),
    SharpEyes(111), // IDA
    ManaReflection(112),
    Attract(113), // 320 IDA
    NoBulletConsume(114), // 320 IDA
    Infinity(115),
    AdvancedBless(116), // 320 IDA
    ILLUSION(117), //1.2.1029
    Blind(118), //de : 0x20000000, 13
    Concentration(119), //1.2.1029
    BanMap(120), // 320 IDA
    MaxLevelBuff(121),
    MesoUpByItem(122), //1.2.1029 ++
    WealthOfUnion(123), //1.2.284
    RuneOfGreed(124),
    Ghost(125), // 320 IDA
    Barrier(126), // 320 IDA
    ReverseInput(127, 68), // 320 IDA
    ItemUpByItem(128), //1.2.1029 ++
    RespectPImmune(129), // 320 IDA
    RespectMImmune(130), // 320 IDA
    DefenseAtt(131), // 320 IDA
    DefenseState(132), // 320 IDA
    DojangBerserk(133), // 320 IDA
    DojangInvincible(134), // 320 IDA
    DojangShield(135), // 320 IDA
    SoulMasterFinal(136), //1.2.1029 ++
    WindBreakerFinal(137), // 320 IDA
    ElementalReset(138),
    HideAttack(139), // 320 IDA
    EVENT_RATE(140), //1.2.1029 ++
    AranCombo(141),
    AuraRecovery(142),
    Unk136(143),
    BodyPressure(144),
    RepeatEffect(145), // 320 IDA
    ExpBuffRate(146), // 342 sniffer 확실 
    StopPortion(147), // 320 IDA
    StopMotion(148), // 320 IDA
    Fear(149), // 320 IDA
    HiddenPieceOn(150), // 320 IDA
    MagicShield(151), // 320 IDA
    ICE_SKILL(152),
    SoulStone(153),
    Flying(154), // IDA
    Frozen(155, 73),
    AssistCharge(156),
    Enrage(157),
    DrawBack(158), // 320 IDA
    NotDamaged(159), // 320 IDA
    FinalCut(160), // 320 IDA
    HowlingParty(161),
    BeastFormDamage(162),
    Dance(163), // 320 IDA

    EnhancedMaxHp(164),
    EnhancedMaxMp(165),
    EnhancedPad(166),
    EnhancedMad(167),
    EnhancedPdd(168),
    PerfectArmor(169),
    UnkBuffStat2(170),
    IncreaseJabelinDam(167),
    HowlingCritical(168),
    HowlingMaxMp(169),
    HowlingDefence(170),
    HowlingEvasion(171),
    PinkbeanMinibeenMove(172), //1.2.1029
    Sneak(173), // IDA

    Mechanic(174), // 320 IDA
    BeastFormMaxHP(175), // de : 11 line
    DiceRoll(176), // IDA
    BlessingArmor(177), // IDA
    DamR(178),
    TeleportMastery(179),
    CombatOrders(180),
    Beholder(181), // 320 IDA
    DispelItemOption(182),
    Inflation(183), // 320 IDA
    OnixDivineProtection(184),
    Web(185), // 320 IDA
    Bless(186),
    TimeBomb(187), // 320 IDA
    DisOrder(188), // 320 IDA
    Thread(189), // 320 IDA
    Team(190), // 320 IDA
    Explosion(191), // 320 IDA
    BUFFLIMIT(192), //1.2.1029 ++
    STR(193), //de : 8000
    INT(194), // de : 4000
    DEX(195), // de : 2000
    LUK(196), // de : 1000
    DISPEL_BY_FIELD(197), //1.2.1029 ++ 335~
    DarkTornado(198), // 320 IDA
    PVPDamage(199), //1.2.1029 ++
    PVP_SCORE_BONUS(200), //1.2.1029 ++
    PVP_INVINCIBLE(201),
    PvPRaceEffect(202), // 320 IDA
    WeaknessMdamage(203), // 320 IDA
    Frozen2(204, 73), // 320 IDA
    PVP_DAMAGE_SKILL(205), //1.2.1029 ++
    AmplifyDamage(206), // 320 IDA
    Shock(207), // 320 IDA
    InfinityForce(208),
    IncMaxHP(209),
    IncMaxMP(210),
    HolyMagicShell(211), // 342 sniffer 211 확실
    KeyDownTimeIgnore(212),
    ArcaneAim(213),
    MasterMagicOn(214),
    Asr(215),
    Ter(216),
    DamAbsorbShield(217), // 320 IDA
    DevilishPower(218), // 342 확실
    Roulette(219),
    SpiritLink(220), // 320 IDA
    AsrRByItem(221),
    Event(222), // 320 IDA
    CriticalIncrease(223),
    DropItemRate(224),
    DropRate(225),
    ItemInvincible(226),
    Awake(227),
    ItemCritical(228),
    ItemEvade(229),
    Event2(230), // 320 IDA
    DrainHp(231), // IDA
    IncDefenseR(232),
    IncTerR(233),
    IncAsrR(234),
    DeathMark(235), // 320 IDA
    Infiltrate(236),
    Lapidification(237), // 320 IDA
    VenomSnake(238), // 320 IDA
    CarnivalAttack(239), // 310 IDA
    CarnivalDefence(240),
    CarnivalExp(241),
    SlowAttack(242),
    PyramidEffect(243), // 320 IDA
    KillingPoint(244), // 320 IDA
    Unk248(245),
    KeyDownMoving(246), // 320 IDA
    IgnoreTargetDEF(247), // 320 IDA
    ReviveOnce(248),
    Invisible(249), // 320 IDA
    EnrageCr(250),
    EnrageCrDamMin(251),
    Judgement(252), // 320 IDA
    DojangLuckyBonus(253),
    PainMark(254), // 320 IDA
    Magnet(255), // 320 IDA
    MagnetArea(256), // 320 IDA
    GuidedArrow(257),
    UnkBuffStat4(258),
    BlessMark(259),
    BonusAttack(260),
    UnkBuffStat5(261),
    FlowOfFight(262),
    GrandCrossSize(263),
    LuckOfUnion(264),
    VampDeath(265), // 320 IDA
    BlessingArmorIncPad(266), // IDA
    KeyDownAreaMoving(267), // 320 IDA
    Larkness(268), // 320 IDA
    StackBuff(269), // 320 IDA
    BlessOfDarkness(270), // 320 IDA
    AntiMagicShell(271), // 320 IDA
    LifeTidal(272), // IDA
    HitCriDamR(273),
    SmashStack(274), // 320 IDA
    RoburstArmor(275),
    ReshuffleSwitch(276), // 320 IDA
    SpecialAction(277), // 320 IDA
    VampDeathSummon(278), // 320 IDA
    StopForceAtominfo(279), // 320 IDA
    SoulGazeCriDamR(280), // 320 IDA
    Affinity(281),
    PowerTransferGauge(282), // 320 IDA
    AffinitySlug(283), // 320 IDA
    Trinity(284), // IDA
    INCMAXDAMAGE(285), //1.2.273
    BossShield(286),
    MobZoneState(287), // 320 IDA
    GiveMeHeal(288), // 320 IDA
    TouchMe(289), // 320 IDA
    Contagion(290), // 320 IDA
    ComboUnlimited(291), // 320 IDA
    SoulExalt(292), // 320 IDA
    IgnorePCounter(293), // 320 IDA
    IgnoreAllCounter(294), // 320 IDA
    IgnorePImmune(295), // 320 IDA
    IgnoreAllImmune(296), // 320 IDA  //일단여까지

    // FinalJudgement(300), // 320 IDA
    UnkBuffStat6(297), // 320 IDA
    FireAura(298), // 320 IDA
    VengeanceOfAngel(299), // 320 IDA
    HeavensDoor(300), // 320 IDA
    Preparation(301),
    BullsEye(302),
    IncEffectHPPotion(303),
    IncEffectMPPotion(304),
    BleedingToxin(305),
    IgnoreMobDamR(306), // 320 IDA
    Asura(307), // 320 IDA
    MegaSmasher(308), // 320 IDA
    FlipTheCoin(309),
    UnityOfPower(310), // 320 IDA
    Stimulate(311), // 320 IDA
    ReturnTeleport(312), // 320 IDA
    DropRIncrease(313), // IDA
    IgnoreMobPdpR(314), // IDA
    BdR(315), // IDA
    CapDebuff(316), // 320 IDA
    Exceed(317),
    DiabloicRecovery(318),
    FinalAttackProp(319),
    Unk319(320),
    OverloadCount(321), // 320 IDA
    Buckshot(322),
    FireBomb(323), // 320 IDA
    HalfstatByDebuff(324), // 320 IDA
    SurplusSupply(325), // 320 IDA
    SetBaseDamage(326),
    EvaR(327),
    NewFlying(328), // 342 sniff 328나옴
    AmaranthGenerator(329), // 320 IDA
    OnCapsule(330), // 342 sniff 330임 
    CygnusElementSkill(331), // 320 IDA
    StrikerHyperElectric(332), // 320 IDA
    EventPointAbsorb(333), // 320 IDA
    EventAssemble(334), // 320 IDA
    StormBringer(335), //maybe
    AddAvoid(336),
    AddAcc(337),
    Albatross(338), // 320 IDA
    Translucence(339), // 320 IDA
    PoseType(340), // 320 IDA
    LightOfSpirit(341), // 320 IDA
    ElementSoul(342), // 320 IDA
    GlimmeringTime(343), // 320 IDA
    SolunaTime(344),
    WindWalk(345),
    SoulMP(346), // IDA
    FullSoulMP(347), // 320 IDA
    SoulSkillDamageUp(348),
    ElementalCharge(349), // 320 IDA
    Listonation(350),
    CrossOverChain(351), // 320 IDA
    ChargeBuff(352),
    Reincarnation(353), // 320 IDA
    ChillingStep(354), // 342 sniffer 확실
    DotBasedBuff(355),
    BlessingAnsanble(356),
    ComboCostInc(357),
    ExtremeArchery(358), // IDA
    NaviFlying(359), // 320 IDA
    QuiverCatridge(360), // 320 IDA
    AdvancedQuiver(361),
    UserControlMob(362), // IDA
    ImmuneBarrier(363), // 320 IDA
    ArmorPiercing(364), // 320 IDA
    CriticalGrowing(365), // IDA
    CardinalMark(366),
    QuickDraw(367), // 307 sniff
    BowMasterConcentration(368), // 307 sniff
    TimeFastABuff(369), // ok
    TimeFastBBuff(370), // ok
    GatherDropR(371),
    AimBox2D(372),
    TrueSniping(373), // 307
    DebuffTolerance(374),
    Unk376(375),
    DotHealHPPerSecond(376), // IDA
    DotHealMPPerSecond(377), // 룬 패치 이후 새로 추가됨.
    SpiritGuard(378), // 320 IDA
    PreReviveOnce(379), // 273 : 362
    SetBaseDamageByBuff(380), // ok
    LimitMP(381), // ok
    ReflectDamR(382), //
    ComboTempest(383), // IDA
    MHPCutR(384), //
    MMPCutR(385), //
    SelfWeakness(386), // 1029
    ElementDarkness(387), // 273 : 370
    FlareTrick(388), // 1029
    Ember(389), // 1029
    Dominion(390), // 273 : 373
    SiphonVitality(391), // 296 : 0x80000000
    DarknessAscension(392), // 296 : 0x40000000
    BossWaitingLinesBuff(393), // 276 : 378, 296 : 0x20000000
    DamageReduce(394), // 1029
    ShadowServant(395), //307 sniff
    ShadowIllusion(396), //273 : 379
    KnockBack(397), //IDA
    IgnisRore(398),
    ComplusionSlant(399), //320 IDA
    JaguarSummoned(400), // 320 IDA
    JaguarCount(401),
    SSFShootingAttack(402), // new
    DEVIL_CRY(403),
    ShieldAttack(404), // IDA
    DarkLighting(405), // 320 IDA
    AttackCountX(406), // 320 IDA
    BMageDeath(407),
    BombTime(408),
    NoDebuff(409),
    BattlePvP_Mike_Shield(410),
    BattlePvP_Mike_Bugle(411),
    AegisSystem(412),
    SoulSeekerExpert(413),
    HiddenPossession(414),
    ShadowBatt(415),
    MarkofNightLord(416),
    WizardIgnite(417),
    FireBarrier(418), // 320 IDA
    CHANGE_FOXMAN(419),
    HolyUnity(420), //1.2.284
    DemonFrenzy(421),
    ShadowSpear(422),
    UnkBuffStat9(423),
    Ellision(424),
    QuiverFullBurst(425),
    LuminousPerfusion(426),
    WildGrenadier(427), // 308 Sniff
    GrandCross(428),
    Unk432(429),
    BattlePvP_Helena_Mark(430), // 320 IDA
    BattlePvP_Helena_WindSpirit(431),
    BattlePvP_LangE_Protection(432), // 320 IDA
    BattlePvP_LeeMalNyun_ScaleUp(433),
    BattlePvP_Revive(434),
    PinkbeanAttackBuff(435), // IDA
    PinkbeanRelax(436),
    PinkbeanRollingGrade(437), // 342 -3 437 ok
    PinkbeanYoYoStack(438),
    UnkBuffStat10(439),
    RandAreaAttack(440),
    NextAttackEnhance(441),
    BeyondNextAttackProb(442),
    NautilusFinalAttack(443),
    ViperTimeLeap(444),
    RoyalGuardState(445),
    RoyalGuardPrepare(446),
    MichaelSoulLink(447), // 320 IDA
    Unk451(448),
    TryflingWarm(449),
    AddRange(450),
    KinesisPsychicPoint(451), //334확실
    KinesisPsychicOver(452),
    KinesisPsychicShield(453),
    KinesisIncMastery(454),
    KinesisPsychicEnergeShield(455), // 320 IDA
    BladeStance(456), // 320 IDA
    DebuffActiveHp(457),
    DebuffIncHp(458),
    MortalBlow(459),
    SoulResonance(460), // 308 Sniff
    Fever(461), // 320 IDA
    SikSin(462),
    TeleportMasteryRange(463),
    FixCooltime(464),
    IncMobRateDummy(465),
    AdrenalinBoost(466), // 320 IDA
    AranSmashSwing(467), // 320 IDA
    AranDrain(468),
    AranBoostEndHunt(469),
    HiddenHyperLinkMaximization(470),
    RWCylinder(471),
    RWCombination(472),
    UnkBuffStat12(473),
    RwMagnumBlow(474),
    RwBarrier(475),
    RWBarrierHeal(476),
    RWMaximizeCannon(477),
    RWOverHeat(478),
    UsingScouter(479),
    RWMovingEvar(480),
    Stigma(481),
    InstallMaha(482), //334?
    CooldownHeavensDoor(483), //487?
    CooldownRune(484),
    PinPointRocket(485),
    UnkBuffStat13(486),
    Transform(487),
    EnergyBurst(488),
    Striker1st(489),
    BulletParty(490),
    SelectDice(491), //new
    Pray(492),
    ChainArtsFury(493),
    DamageDecreaseWithHP(494),
    Unk498(495),
    AuraWeapon(496),
    OverloadMana(497),
    RhoAias(498), // 307 Snif
    PsychicTornado(499),
    SpreadThrow(500),
    HowlingGale(501),
    VMatrixStackBuff(502), // 블랙 매직 알터, 빅 휴즈 캐논볼, 퓨리어스 차지, 소울 오브 크리스탈, 기타 등등..
    ShadowAssult(503), // 312 Sniff 507
    MultipleOption(504), // 312 Sniff
    Unk508(505),
    BlitzShield(506), // 342 -3
    SplitArrow(507), // ok
    FreudsProtection(508),
    Overload(509), // 320 확실
    Spotlight(510), //320 확실
    UnkBuffStat16(511), // 캐릭터 사라지는거
    WeaponVariety(512), // 웨폰 버라이어티~ 확실
    GloryWing(513), // (아크 체공, 일리움 공중날기, 일리움 : 크래프트 오브) //517 일리움 화면 주위 그거 GloryWing
    ShadowerDebuff(514),
    OverDrive(515), // 520 312 Sniff
    Etherealform(516),
    ReadyToDie(517),
    CriticalReinForce(518),//?
    CurseOfCreation(519), //창조의 저주
    CurseOfDestruction(520), //파괴의 저주
    UnkBuffStat17(521), //검마4페
    BodyOfSteal(522),//unk526
    UnkBuffStat18(523),
    UnkBuffStat19(524),
    HarmonyLink(525),
    FastCharge(526),
    UnkBuffStat20(527),
    CrystalBattery(528),
    Deus(529),
    CrystalChargeMax(530),//? 이쪽부근모름
    UnkBuffStat22(531),//?
    Unk536(532),
    Unk537(533),
    Unk538(534),
    SpectorGauge(535),
    SpectorTransForm(536),
    PlainBuff(537),
    ScarletBuff(538),
    GustBuff(539),
    AbyssBuff(540),
    ComingDeath(541),
    FightJazz(542),
    ChargeSpellAmplification(543),
    InfinitySpell(544),
    MagicCircuitFullDrive(545),
    LinkOfArk(546),
    MemoryOfSource(547),
    UnkBuffStat26(548),
    WillPoison(549), // 310 Sniff
    Unk556(550),
    UnkBuffStat28(551), // 알 수 없는 이유로 사용 실패
    CooltimeHolyMagicShell(552),
    Striker3rd(553),
    ComboInstict(554), // 310 Sniff
    WindWall(555), // 320 IDA 
    UnkBuffStat29(556),
    SwordOfSoulLight(557),
    MarkOfPhantomStack(558),
    MarkOfPhantomDebuff(559),
    Unk565(560),
    Unk566(561),
    Unk567(562),
    Unk568(563),
    Unk569(564), // 눈내리는 버프스탯
    EventSpecialSkill(565), // 코인 모으기용 버프스탯인듯, 네오 머쉬룸 와칭도 동일
    PmdReduce(566),
    ForbidOpPotion(567),
    ForbidEquipChange(568),
    YalBuff(569), // 파괴의 얄다바오트 //320 IDA
    IonBuff(570), // 창조의 아이온 // 320 IDA
    Unk576(571),
    UnkBuffStat36(572),
    Unk578(573),
    Protective(574),
    UnkBuffStat38(575), // 블러드 피스트?
    AncientGuidance(576),
    UnkBuffStat39(577), // 팅김
    UnkBuffStat40(578), // 팅김
    UnkBuffStat41(579),
    UnkBuffStat42(580),
    UnkBuffStat43(581), // 파랑 깜빡이
    UnkBuffStat44(582),
    Bless5th(583), // ?
    UnkBuffStat45(584),
    UnkBuffStat46(585),
    UnkBuffStat47(586),
    UnkBuffStat48(587),
    UnkBuffStat49(588),
    UnkBuffStat50(589), // 팅김
    PapyrusOfLuck(590),
    HoyoungThirdProperty(591),
    TidalForce(592),
    Alterego(593),
    AltergoReinforce(594),
    ButterflyDream(595),
    Sungi(596),
    UnkBuffStat51(597),
    EmpiricalKnowledge(598),
    UnkBuffStat52(599),
    UnkBuffStat53(600),
    Graffiti(601),
    DreamDowon(602),
    AdelGauge(604),
    Creation(605),
    Dike(606),
    Wonder(607),
    Restore(608),
    Novility(609), // 342 sniffer 609확실
    AdelResonance(610),
    RuneOfPure(611), // 332
    UnkBuffStat56(612),
    DuskDarkness(613),
    //332 Aura changed (614 ~ 623)
    YellowAura(614),
    DrainAura(615),
    BlueAura(616),
    DarkAura(617),
    DebuffAura(618),
    UnionAura(619), // 위에 5개도 같이 보내줌
    IceAura(620), //332 changed
    KnightsAura(621), // 332 changed
    UnkBuffStat622(622),//??343 하나생김 오라 -1씩
    ZeroAuraStr(623), // 332 changed
    ZeroAuraSpd(624), // 332 changed
    UnkBuffStat57(625),
    Revenant(626),
    RevenantDamage(627),
    PhotonRay(630),
    RoyalKnights(633),
    RepeatinCartrige(636),
    ThrowBlasting(638),
    DarknessAura(640),
    UnkBuffStat60(644),
    RelicUnbound(645),
    UnkBuffStat58(646),
    unk647(647),
    Malice(649),
    Possession(650),
    DeathBlessing(651),
    ThanatosDescent(652),
    RemainInsence(653),
    GripOfAgony(654),
    DragonPang(655),
    KainLink(659),
    //624, 625 333++

    //카인생겨서 + 12   343 +1
    EnergyCharged(664), //?
    DashJump(665),
    DashSpeed(666),
    RideVehicle(667),
    PartyBooster(668),
    GuidedBullet(669),
    Undead(670, 1, 133),
    RideVehicleExpire(671),
    RelikGauge(672),
    Grave(673),
    CountPlus1(674);

    private static final long serialVersionUID = 0L;
    private int buffstat;
    private int first;
    private boolean stacked = false;
    private int disease;
    private int flag;
    // [8] [7] [6] [5] [4] [3] [2] [1]
    // [0] [1] [2] [3] [4] [5] [6] [7]

    private MapleBuffStat(int flag) {
        this.buffstat = (1 << (31 - (flag % 32)));
        this.setFirst(GameConstants.MAX_BUFFSTAT - (byte) Math.floor(flag / 32));
        this.setStacked(name().startsWith("Indie") || name().startsWith("Pyramid"));
        this.setFlag(flag);
    }

    private MapleBuffStat(int flag, int disease) {
        this.buffstat = (1 << (31 - (flag % 32)));
        this.setFirst(GameConstants.MAX_BUFFSTAT - (byte) Math.floor(flag / 32));
        this.setStacked(name().startsWith("Indie") || name().startsWith("Pyramid"));
        this.setFlag(flag);
        this.disease = disease;
    }

    private MapleBuffStat(int buffstat, int first, boolean stacked) {
        this.buffstat = buffstat;
        this.setFirst(first);
        this.setStacked(stacked);
    }

    private MapleBuffStat(int buffstat, int first, int disease) {
        this.buffstat = buffstat;
        this.setFirst(first);
        this.disease = disease;
    }

    public final int getPosition() {
        return getFirst();//getPosition(stacked);
    }

    public final int getPosition(boolean stacked) {
        if (!stacked) {
            return getFirst();
        }
        switch (getFirst()) {
            case 16:
                return 0;
            case 15:
                return 1;
            case 14:
                return 2;
            case 13:
                return 3;
            case 12:
                return 4;
            case 11:
                return 5;
            case 10:
                return 6;
            case 9:
                return 7;
            case 8:
                return 8;
            case 7:
                return 9;
            case 6:
                return 10;
            case 5:
                return 11;
            case 4:
                return 12;
            case 3:
                return 13;
            case 2:
                return 14;
            case 1:
                return 15;
            case 0:
                return 16;
        }
        return 0; // none
    }

    public final int getValue() {
        return getBuffstat();
    }

    public final boolean canStack() {
        return isStacked();
    }

    public int getDisease() {
        return disease;
    }

    public static final MapleBuffStat getByFlag(final int flag) {
        for (MapleBuffStat d : MapleBuffStat.values()) {
            if (d.getFlag() == flag) {
                return d;
            }
        }
        return null;
    }

    public static final MapleBuffStat getBySkill(final int skill) {
        for (MapleBuffStat d : MapleBuffStat.values()) {
            if (d.getDisease() == skill) {
                return d;
            }
        }
        return null;
    }

    public static final List<MapleBuffStat> getUnkBuffStats() {
        List<MapleBuffStat> stats = new ArrayList<>();
        for (MapleBuffStat d : MapleBuffStat.values()) {
            if (d.name().startsWith("UnkBuff")) {
                stats.add(d);
            }
        }
        return stats;
    }

    public static final MapleBuffStat getRandom() {
        while (true) {
            for (MapleBuffStat dis : MapleBuffStat.values()) {
                if (Randomizer.nextInt(MapleBuffStat.values().length) == 0) {
                    return dis;
                }
            }
        }
    }

    public static boolean isEncode4Byte(Map<MapleBuffStat, Pair<Integer, Integer>> statups) {
        MapleBuffStat[] stats
                = {
                CarnivalDefence,
                SpiritLink,
                DojangLuckyBonus,
                SoulGazeCriDamR,
                PowerTransferGauge,
                ReturnTeleport,
                ShadowPartner,
                SetBaseDamage,
                QuiverCatridge,
                ImmuneBarrier,
                NaviFlying,
                Dance,
                AranSmashSwing,
                DotHealHPPerSecond,
                SetBaseDamageByBuff,
                MagnetArea,
                MegaSmasher,
                RwBarrier,
                VampDeath,
                RideVehicle,
                RideVehicleExpire,
                Protective,
                BlitzShield,
                UnkBuffStat2,
                UnkBuffStat22,};
        for (MapleBuffStat stat : stats) {
            if (statups.containsKey(stat)) {
                return true;
            }
        }
        return false;
    }

    public boolean isSpecialBuff() {
        switch (this) {
            case EnergyCharged:
            case DashSpeed:
            case DashJump:
            case RideVehicle:
            case PartyBooster:
            case GuidedBullet:
            case Undead:
            case RideVehicleExpire:
            case RelikGauge:
            case Grave:
                return true;
            default:
                return false;
        }
    }

    public int getFlag() {
        return flag;
    }

    public void setFlag(int flag) {
        this.flag = flag;
    }

    public boolean isItemEffect() {
        switch (this) {
            case DropItemRate:
            case ItemUpByItem:
            case MesoUpByItem:
            case ExpBuffRate:
            case WealthOfUnion:
            case LuckOfUnion:
                return true;
            default:
                return false;
        }
    }

    public boolean SpectorEffect() {
        switch (this) {
            case SpectorGauge:
            case SpectorTransForm:
            case PlainBuff:
            case ScarletBuff:
            case GustBuff:
            case AbyssBuff:
                return true;
            default:
                return false;
        }
    }

    public int getBuffstat() {
        return buffstat;
    }

    public void setBuffstat(int buffstat) {
        this.buffstat = buffstat;
    }

    public int getFirst() {
        return first;
    }

    public void setFirst(int first) {
        this.first = first;
    }

    public boolean isStacked() {
        return stacked;
    }

    public void setStacked(boolean stacked) {
        this.stacked = stacked;
    }
}
