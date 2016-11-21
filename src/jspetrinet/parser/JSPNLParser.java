// Generated from JSPNL.g4 by ANTLR 4.5.3

package jspetrinet.parser;

import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.misc.*;
import org.antlr.v4.runtime.tree.*;
import java.util.List;
import java.util.Iterator;
import java.util.ArrayList;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public class JSPNLParser extends Parser {
	static { RuntimeMetaData.checkVersion("4.5.3", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		T__0=1, T__1=2, T__2=3, T__3=4, T__4=5, T__5=6, T__6=7, T__7=8, T__8=9, 
		T__9=10, T__10=11, T__11=12, T__12=13, T__13=14, T__14=15, T__15=16, T__16=17, 
		T__17=18, T__18=19, T__19=20, T__20=21, T__21=22, T__22=23, T__23=24, 
		T__24=25, T__25=26, T__26=27, T__27=28, T__28=29, T__29=30, T__30=31, 
		T__31=32, LOGICAL=33, ID=34, INT=35, FLOAT=36, STRING=37, NEWLINE=38, 
		WS=39, LINE_COMMENT=40, BLOCK_COMMENT=41;
	public static final int
		RULE_prog = 0, RULE_statement = 1, RULE_declaration = 2, RULE_node_declaration = 3, 
		RULE_arc_declaration = 4, RULE_option_list = 5, RULE_option_value = 6, 
		RULE_assign_expression = 7, RULE_expression = 8, RULE_function_expression = 9, 
		RULE_arg_list = 10, RULE_arg_value = 11, RULE_ntoken_expression = 12, 
		RULE_enable_expression = 13, RULE_literal_expression = 14, RULE_place_list = 15, 
		RULE_place_value = 16;
	public static final String[] ruleNames = {
		"prog", "statement", "declaration", "node_declaration", "arc_declaration", 
		"option_list", "option_value", "assign_expression", "expression", "function_expression", 
		"arg_list", "arg_value", "ntoken_expression", "enable_expression", "literal_expression", 
		"place_list", "place_value"
	};

	private static final String[] _LITERAL_NAMES = {
		null, "'place'", "'imm'", "'exp'", "'gen'", "'('", "')'", "'arc'", "'iarc'", 
		"'oarc'", "'harc'", "'to'", "','", "'='", "':='", "'!'", "'+'", "'-'", 
		"'*'", "'/'", "'%'", "'<'", "'<='", "'>'", "'>='", "'=='", "'!='", "'&&'", 
		"'||'", "'ifelse'", "'#'", "'?'", "'<-'"
	};
	private static final String[] _SYMBOLIC_NAMES = {
		null, null, null, null, null, null, null, null, null, null, null, null, 
		null, null, null, null, null, null, null, null, null, null, null, null, 
		null, null, null, null, null, null, null, null, null, "LOGICAL", "ID", 
		"INT", "FLOAT", "STRING", "NEWLINE", "WS", "LINE_COMMENT", "BLOCK_COMMENT"
	};
	public static final Vocabulary VOCABULARY = new VocabularyImpl(_LITERAL_NAMES, _SYMBOLIC_NAMES);

	/**
	 * @deprecated Use {@link #VOCABULARY} instead.
	 */
	@Deprecated
	public static final String[] tokenNames;
	static {
		tokenNames = new String[_SYMBOLIC_NAMES.length];
		for (int i = 0; i < tokenNames.length; i++) {
			tokenNames[i] = VOCABULARY.getLiteralName(i);
			if (tokenNames[i] == null) {
				tokenNames[i] = VOCABULARY.getSymbolicName(i);
			}

			if (tokenNames[i] == null) {
				tokenNames[i] = "<INVALID>";
			}
		}
	}

	@Override
	@Deprecated
	public String[] getTokenNames() {
		return tokenNames;
	}

	@Override

	public Vocabulary getVocabulary() {
		return VOCABULARY;
	}

	@Override
	public String getGrammarFileName() { return "JSPNL.g4"; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String getSerializedATN() { return _serializedATN; }

	@Override
	public ATN getATN() { return _ATN; }

	public JSPNLParser(TokenStream input) {
		super(input);
		_interp = new ParserATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}
	public static class ProgContext extends ParserRuleContext {
		public List<StatementContext> statement() {
			return getRuleContexts(StatementContext.class);
		}
		public StatementContext statement(int i) {
			return getRuleContext(StatementContext.class,i);
		}
		public ProgContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_prog; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof JSPNLListener ) ((JSPNLListener)listener).enterProg(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof JSPNLListener ) ((JSPNLListener)listener).exitProg(this);
		}
	}

	public final ProgContext prog() throws RecognitionException {
		ProgContext _localctx = new ProgContext(_ctx, getState());
		enterRule(_localctx, 0, RULE_prog);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(37);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__0) | (1L << T__1) | (1L << T__2) | (1L << T__3) | (1L << T__4) | (1L << T__6) | (1L << T__7) | (1L << T__8) | (1L << T__9) | (1L << T__14) | (1L << T__15) | (1L << T__16) | (1L << T__28) | (1L << T__29) | (1L << T__30) | (1L << LOGICAL) | (1L << ID) | (1L << INT) | (1L << FLOAT) | (1L << STRING) | (1L << NEWLINE))) != 0)) {
				{
				{
				setState(34);
				statement();
				}
				}
				setState(39);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class StatementContext extends ParserRuleContext {
		public DeclarationContext declaration() {
			return getRuleContext(DeclarationContext.class,0);
		}
		public TerminalNode NEWLINE() { return getToken(JSPNLParser.NEWLINE, 0); }
		public Assign_expressionContext assign_expression() {
			return getRuleContext(Assign_expressionContext.class,0);
		}
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public StatementContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_statement; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof JSPNLListener ) ((JSPNLListener)listener).enterStatement(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof JSPNLListener ) ((JSPNLListener)listener).exitStatement(this);
		}
	}

	public final StatementContext statement() throws RecognitionException {
		StatementContext _localctx = new StatementContext(_ctx, getState());
		enterRule(_localctx, 2, RULE_statement);
		try {
			setState(50);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,1,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(40);
				declaration();
				setState(41);
				match(NEWLINE);
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(43);
				assign_expression();
				setState(44);
				match(NEWLINE);
				}
				break;
			case 3:
				enterOuterAlt(_localctx, 3);
				{
				setState(46);
				expression(0);
				setState(47);
				match(NEWLINE);
				}
				break;
			case 4:
				enterOuterAlt(_localctx, 4);
				{
				setState(49);
				match(NEWLINE);
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class DeclarationContext extends ParserRuleContext {
		public Node_declarationContext node_declaration() {
			return getRuleContext(Node_declarationContext.class,0);
		}
		public Arc_declarationContext arc_declaration() {
			return getRuleContext(Arc_declarationContext.class,0);
		}
		public DeclarationContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_declaration; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof JSPNLListener ) ((JSPNLListener)listener).enterDeclaration(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof JSPNLListener ) ((JSPNLListener)listener).exitDeclaration(this);
		}
	}

	public final DeclarationContext declaration() throws RecognitionException {
		DeclarationContext _localctx = new DeclarationContext(_ctx, getState());
		enterRule(_localctx, 4, RULE_declaration);
		try {
			setState(54);
			switch (_input.LA(1)) {
			case T__0:
			case T__1:
			case T__2:
			case T__3:
				enterOuterAlt(_localctx, 1);
				{
				setState(52);
				node_declaration();
				}
				break;
			case T__6:
			case T__7:
			case T__8:
			case T__9:
				enterOuterAlt(_localctx, 2);
				{
				setState(53);
				arc_declaration();
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Node_declarationContext extends ParserRuleContext {
		public Token type;
		public Token id;
		public TerminalNode ID() { return getToken(JSPNLParser.ID, 0); }
		public Option_listContext option_list() {
			return getRuleContext(Option_listContext.class,0);
		}
		public Node_declarationContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_node_declaration; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof JSPNLListener ) ((JSPNLListener)listener).enterNode_declaration(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof JSPNLListener ) ((JSPNLListener)listener).exitNode_declaration(this);
		}
	}

	public final Node_declarationContext node_declaration() throws RecognitionException {
		Node_declarationContext _localctx = new Node_declarationContext(_ctx, getState());
		enterRule(_localctx, 6, RULE_node_declaration);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(56);
			((Node_declarationContext)_localctx).type = _input.LT(1);
			_la = _input.LA(1);
			if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__0) | (1L << T__1) | (1L << T__2) | (1L << T__3))) != 0)) ) {
				((Node_declarationContext)_localctx).type = (Token)_errHandler.recoverInline(this);
			} else {
				consume();
			}
			setState(57);
			((Node_declarationContext)_localctx).id = match(ID);
			setState(62);
			_la = _input.LA(1);
			if (_la==T__4) {
				{
				setState(58);
				match(T__4);
				setState(59);
				option_list();
				setState(60);
				match(T__5);
				}
			}

			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Arc_declarationContext extends ParserRuleContext {
		public Token type;
		public Token srcName;
		public Token destName;
		public List<TerminalNode> ID() { return getTokens(JSPNLParser.ID); }
		public TerminalNode ID(int i) {
			return getToken(JSPNLParser.ID, i);
		}
		public Option_listContext option_list() {
			return getRuleContext(Option_listContext.class,0);
		}
		public Arc_declarationContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_arc_declaration; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof JSPNLListener ) ((JSPNLListener)listener).enterArc_declaration(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof JSPNLListener ) ((JSPNLListener)listener).exitArc_declaration(this);
		}
	}

	public final Arc_declarationContext arc_declaration() throws RecognitionException {
		Arc_declarationContext _localctx = new Arc_declarationContext(_ctx, getState());
		enterRule(_localctx, 8, RULE_arc_declaration);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(64);
			((Arc_declarationContext)_localctx).type = _input.LT(1);
			_la = _input.LA(1);
			if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__6) | (1L << T__7) | (1L << T__8) | (1L << T__9))) != 0)) ) {
				((Arc_declarationContext)_localctx).type = (Token)_errHandler.recoverInline(this);
			} else {
				consume();
			}
			setState(65);
			((Arc_declarationContext)_localctx).srcName = match(ID);
			setState(66);
			match(T__10);
			setState(67);
			((Arc_declarationContext)_localctx).destName = match(ID);
			setState(72);
			_la = _input.LA(1);
			if (_la==T__4) {
				{
				setState(68);
				match(T__4);
				setState(69);
				option_list();
				setState(70);
				match(T__5);
				}
			}

			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Option_listContext extends ParserRuleContext {
		public Option_valueContext option_value() {
			return getRuleContext(Option_valueContext.class,0);
		}
		public List<Option_listContext> option_list() {
			return getRuleContexts(Option_listContext.class);
		}
		public Option_listContext option_list(int i) {
			return getRuleContext(Option_listContext.class,i);
		}
		public Option_listContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_option_list; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof JSPNLListener ) ((JSPNLListener)listener).enterOption_list(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof JSPNLListener ) ((JSPNLListener)listener).exitOption_list(this);
		}
	}

	public final Option_listContext option_list() throws RecognitionException {
		Option_listContext _localctx = new Option_listContext(_ctx, getState());
		enterRule(_localctx, 10, RULE_option_list);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(74);
			option_value();
			setState(79);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,5,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					{
					{
					setState(75);
					match(T__11);
					setState(76);
					option_list();
					}
					} 
				}
				setState(81);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,5,_ctx);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Option_valueContext extends ParserRuleContext {
		public Assign_expressionContext assign_expression() {
			return getRuleContext(Assign_expressionContext.class,0);
		}
		public Option_valueContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_option_value; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof JSPNLListener ) ((JSPNLListener)listener).enterOption_value(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof JSPNLListener ) ((JSPNLListener)listener).exitOption_value(this);
		}
	}

	public final Option_valueContext option_value() throws RecognitionException {
		Option_valueContext _localctx = new Option_valueContext(_ctx, getState());
		enterRule(_localctx, 12, RULE_option_value);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(82);
			assign_expression();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Assign_expressionContext extends ParserRuleContext {
		public int type;
		public Token id;
		public Token op;
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public TerminalNode ID() { return getToken(JSPNLParser.ID, 0); }
		public Assign_expressionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_assign_expression; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof JSPNLListener ) ((JSPNLListener)listener).enterAssign_expression(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof JSPNLListener ) ((JSPNLListener)listener).exitAssign_expression(this);
		}
	}

	public final Assign_expressionContext assign_expression() throws RecognitionException {
		Assign_expressionContext _localctx = new Assign_expressionContext(_ctx, getState());
		enterRule(_localctx, 14, RULE_assign_expression);
		try {
			setState(94);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,6,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(84);
				((Assign_expressionContext)_localctx).id = match(ID);
				setState(85);
				((Assign_expressionContext)_localctx).op = match(T__12);
				setState(86);
				expression(0);
				 ((Assign_expressionContext)_localctx).type =  1; 
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(89);
				((Assign_expressionContext)_localctx).id = match(ID);
				setState(90);
				((Assign_expressionContext)_localctx).op = match(T__13);
				setState(91);
				expression(0);
				 ((Assign_expressionContext)_localctx).type =  2; 
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ExpressionContext extends ParserRuleContext {
		public int type;
		public Token op;
		public Token id;
		public List<ExpressionContext> expression() {
			return getRuleContexts(ExpressionContext.class);
		}
		public ExpressionContext expression(int i) {
			return getRuleContext(ExpressionContext.class,i);
		}
		public Function_expressionContext function_expression() {
			return getRuleContext(Function_expressionContext.class,0);
		}
		public Ntoken_expressionContext ntoken_expression() {
			return getRuleContext(Ntoken_expressionContext.class,0);
		}
		public Enable_expressionContext enable_expression() {
			return getRuleContext(Enable_expressionContext.class,0);
		}
		public Literal_expressionContext literal_expression() {
			return getRuleContext(Literal_expressionContext.class,0);
		}
		public TerminalNode ID() { return getToken(JSPNLParser.ID, 0); }
		public Place_listContext place_list() {
			return getRuleContext(Place_listContext.class,0);
		}
		public ExpressionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_expression; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof JSPNLListener ) ((JSPNLListener)listener).enterExpression(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof JSPNLListener ) ((JSPNLListener)listener).exitExpression(this);
		}
	}

	public final ExpressionContext expression() throws RecognitionException {
		return expression(0);
	}

	private ExpressionContext expression(int _p) throws RecognitionException {
		ParserRuleContext _parentctx = _ctx;
		int _parentState = getState();
		ExpressionContext _localctx = new ExpressionContext(_ctx, _parentState);
		ExpressionContext _prevctx = _localctx;
		int _startState = 16;
		enterRecursionRule(_localctx, 16, RULE_expression, _p);
		int _la;
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(133);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,7,_ctx) ) {
			case 1:
				{
				setState(97);
				((ExpressionContext)_localctx).op = _input.LT(1);
				_la = _input.LA(1);
				if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__14) | (1L << T__15) | (1L << T__16))) != 0)) ) {
					((ExpressionContext)_localctx).op = (Token)_errHandler.recoverInline(this);
				} else {
					consume();
				}
				setState(98);
				expression(15);
				 ((ExpressionContext)_localctx).type =  1; 
				}
				break;
			case 2:
				{
				setState(101);
				((ExpressionContext)_localctx).op = match(T__28);
				setState(102);
				match(T__4);
				setState(103);
				expression(0);
				setState(104);
				match(T__11);
				setState(105);
				expression(0);
				setState(106);
				match(T__11);
				setState(107);
				expression(0);
				setState(108);
				match(T__5);
				 ((ExpressionContext)_localctx).type =  8; 
				}
				break;
			case 3:
				{
				setState(111);
				function_expression();
				 ((ExpressionContext)_localctx).type =  9; 
				}
				break;
			case 4:
				{
				setState(114);
				ntoken_expression();
				 ((ExpressionContext)_localctx).type =  10; 
				}
				break;
			case 5:
				{
				setState(117);
				enable_expression();
				 ((ExpressionContext)_localctx).type =  14; 
				}
				break;
			case 6:
				{
				setState(120);
				literal_expression();
				 ((ExpressionContext)_localctx).type =  11; 
				}
				break;
			case 7:
				{
				setState(123);
				((ExpressionContext)_localctx).id = match(ID);
				 ((ExpressionContext)_localctx).type =  12; 
				}
				break;
			case 8:
				{
				setState(125);
				match(T__4);
				setState(126);
				expression(0);
				setState(127);
				match(T__5);
				 ((ExpressionContext)_localctx).type =  13; 
				}
				break;
			case 9:
				{
				setState(130);
				place_list();
				 ((ExpressionContext)_localctx).type =  15; 
				}
				break;
			}
			_ctx.stop = _input.LT(-1);
			setState(167);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,9,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					if ( _parseListeners!=null ) triggerExitRuleEvent();
					_prevctx = _localctx;
					{
					setState(165);
					_errHandler.sync(this);
					switch ( getInterpreter().adaptivePredict(_input,8,_ctx) ) {
					case 1:
						{
						_localctx = new ExpressionContext(_parentctx, _parentState);
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(135);
						if (!(precpred(_ctx, 14))) throw new FailedPredicateException(this, "precpred(_ctx, 14)");
						setState(136);
						((ExpressionContext)_localctx).op = _input.LT(1);
						_la = _input.LA(1);
						if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__17) | (1L << T__18) | (1L << T__19))) != 0)) ) {
							((ExpressionContext)_localctx).op = (Token)_errHandler.recoverInline(this);
						} else {
							consume();
						}
						setState(137);
						expression(15);
						 ((ExpressionContext)_localctx).type =  2; 
						}
						break;
					case 2:
						{
						_localctx = new ExpressionContext(_parentctx, _parentState);
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(140);
						if (!(precpred(_ctx, 13))) throw new FailedPredicateException(this, "precpred(_ctx, 13)");
						setState(141);
						((ExpressionContext)_localctx).op = _input.LT(1);
						_la = _input.LA(1);
						if ( !(_la==T__15 || _la==T__16) ) {
							((ExpressionContext)_localctx).op = (Token)_errHandler.recoverInline(this);
						} else {
							consume();
						}
						setState(142);
						expression(14);
						 ((ExpressionContext)_localctx).type =  3; 
						}
						break;
					case 3:
						{
						_localctx = new ExpressionContext(_parentctx, _parentState);
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(145);
						if (!(precpred(_ctx, 12))) throw new FailedPredicateException(this, "precpred(_ctx, 12)");
						setState(146);
						((ExpressionContext)_localctx).op = _input.LT(1);
						_la = _input.LA(1);
						if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__20) | (1L << T__21) | (1L << T__22) | (1L << T__23))) != 0)) ) {
							((ExpressionContext)_localctx).op = (Token)_errHandler.recoverInline(this);
						} else {
							consume();
						}
						setState(147);
						expression(13);
						 ((ExpressionContext)_localctx).type =  4; 
						}
						break;
					case 4:
						{
						_localctx = new ExpressionContext(_parentctx, _parentState);
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(150);
						if (!(precpred(_ctx, 11))) throw new FailedPredicateException(this, "precpred(_ctx, 11)");
						setState(151);
						((ExpressionContext)_localctx).op = _input.LT(1);
						_la = _input.LA(1);
						if ( !(_la==T__24 || _la==T__25) ) {
							((ExpressionContext)_localctx).op = (Token)_errHandler.recoverInline(this);
						} else {
							consume();
						}
						setState(152);
						expression(12);
						 ((ExpressionContext)_localctx).type =  5; 
						}
						break;
					case 5:
						{
						_localctx = new ExpressionContext(_parentctx, _parentState);
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(155);
						if (!(precpred(_ctx, 10))) throw new FailedPredicateException(this, "precpred(_ctx, 10)");
						setState(156);
						((ExpressionContext)_localctx).op = match(T__26);
						setState(157);
						expression(11);
						 ((ExpressionContext)_localctx).type =  6; 
						}
						break;
					case 6:
						{
						_localctx = new ExpressionContext(_parentctx, _parentState);
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(160);
						if (!(precpred(_ctx, 9))) throw new FailedPredicateException(this, "precpred(_ctx, 9)");
						setState(161);
						((ExpressionContext)_localctx).op = match(T__27);
						setState(162);
						expression(10);
						 ((ExpressionContext)_localctx).type =  7; 
						}
						break;
					}
					} 
				}
				setState(169);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,9,_ctx);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			unrollRecursionContexts(_parentctx);
		}
		return _localctx;
	}

	public static class Function_expressionContext extends ParserRuleContext {
		public Token id;
		public Arg_listContext arg_list() {
			return getRuleContext(Arg_listContext.class,0);
		}
		public TerminalNode ID() { return getToken(JSPNLParser.ID, 0); }
		public Function_expressionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_function_expression; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof JSPNLListener ) ((JSPNLListener)listener).enterFunction_expression(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof JSPNLListener ) ((JSPNLListener)listener).exitFunction_expression(this);
		}
	}

	public final Function_expressionContext function_expression() throws RecognitionException {
		Function_expressionContext _localctx = new Function_expressionContext(_ctx, getState());
		enterRule(_localctx, 18, RULE_function_expression);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(170);
			((Function_expressionContext)_localctx).id = match(ID);
			setState(171);
			match(T__4);
			setState(172);
			arg_list();
			setState(173);
			match(T__5);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Arg_listContext extends ParserRuleContext {
		public Arg_valueContext arg_value() {
			return getRuleContext(Arg_valueContext.class,0);
		}
		public List<Arg_listContext> arg_list() {
			return getRuleContexts(Arg_listContext.class);
		}
		public Arg_listContext arg_list(int i) {
			return getRuleContext(Arg_listContext.class,i);
		}
		public Arg_listContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_arg_list; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof JSPNLListener ) ((JSPNLListener)listener).enterArg_list(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof JSPNLListener ) ((JSPNLListener)listener).exitArg_list(this);
		}
	}

	public final Arg_listContext arg_list() throws RecognitionException {
		Arg_listContext _localctx = new Arg_listContext(_ctx, getState());
		enterRule(_localctx, 20, RULE_arg_list);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(175);
			arg_value();
			setState(180);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,10,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					{
					{
					setState(176);
					match(T__11);
					setState(177);
					arg_list();
					}
					} 
				}
				setState(182);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,10,_ctx);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Arg_valueContext extends ParserRuleContext {
		public ExpressionContext val;
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public Arg_valueContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_arg_value; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof JSPNLListener ) ((JSPNLListener)listener).enterArg_value(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof JSPNLListener ) ((JSPNLListener)listener).exitArg_value(this);
		}
	}

	public final Arg_valueContext arg_value() throws RecognitionException {
		Arg_valueContext _localctx = new Arg_valueContext(_ctx, getState());
		enterRule(_localctx, 22, RULE_arg_value);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(183);
			((Arg_valueContext)_localctx).val = expression(0);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Ntoken_expressionContext extends ParserRuleContext {
		public Token id;
		public TerminalNode ID() { return getToken(JSPNLParser.ID, 0); }
		public Ntoken_expressionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_ntoken_expression; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof JSPNLListener ) ((JSPNLListener)listener).enterNtoken_expression(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof JSPNLListener ) ((JSPNLListener)listener).exitNtoken_expression(this);
		}
	}

	public final Ntoken_expressionContext ntoken_expression() throws RecognitionException {
		Ntoken_expressionContext _localctx = new Ntoken_expressionContext(_ctx, getState());
		enterRule(_localctx, 24, RULE_ntoken_expression);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(185);
			match(T__29);
			setState(186);
			((Ntoken_expressionContext)_localctx).id = match(ID);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Enable_expressionContext extends ParserRuleContext {
		public Token id;
		public TerminalNode ID() { return getToken(JSPNLParser.ID, 0); }
		public Enable_expressionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_enable_expression; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof JSPNLListener ) ((JSPNLListener)listener).enterEnable_expression(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof JSPNLListener ) ((JSPNLListener)listener).exitEnable_expression(this);
		}
	}

	public final Enable_expressionContext enable_expression() throws RecognitionException {
		Enable_expressionContext _localctx = new Enable_expressionContext(_ctx, getState());
		enterRule(_localctx, 26, RULE_enable_expression);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(188);
			match(T__30);
			setState(189);
			((Enable_expressionContext)_localctx).id = match(ID);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Literal_expressionContext extends ParserRuleContext {
		public int type;
		public Token val;
		public TerminalNode INT() { return getToken(JSPNLParser.INT, 0); }
		public TerminalNode FLOAT() { return getToken(JSPNLParser.FLOAT, 0); }
		public TerminalNode LOGICAL() { return getToken(JSPNLParser.LOGICAL, 0); }
		public TerminalNode STRING() { return getToken(JSPNLParser.STRING, 0); }
		public Literal_expressionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_literal_expression; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof JSPNLListener ) ((JSPNLListener)listener).enterLiteral_expression(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof JSPNLListener ) ((JSPNLListener)listener).exitLiteral_expression(this);
		}
	}

	public final Literal_expressionContext literal_expression() throws RecognitionException {
		Literal_expressionContext _localctx = new Literal_expressionContext(_ctx, getState());
		enterRule(_localctx, 28, RULE_literal_expression);
		try {
			setState(199);
			switch (_input.LA(1)) {
			case INT:
				enterOuterAlt(_localctx, 1);
				{
				setState(191);
				((Literal_expressionContext)_localctx).val = match(INT);
				 ((Literal_expressionContext)_localctx).type =  1; 
				}
				break;
			case FLOAT:
				enterOuterAlt(_localctx, 2);
				{
				setState(193);
				((Literal_expressionContext)_localctx).val = match(FLOAT);
				 ((Literal_expressionContext)_localctx).type =  2; 
				}
				break;
			case LOGICAL:
				enterOuterAlt(_localctx, 3);
				{
				setState(195);
				((Literal_expressionContext)_localctx).val = match(LOGICAL);
				 ((Literal_expressionContext)_localctx).type =  3; 
				}
				break;
			case STRING:
				enterOuterAlt(_localctx, 4);
				{
				setState(197);
				((Literal_expressionContext)_localctx).val = match(STRING);
				 ((Literal_expressionContext)_localctx).type =  4; 
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Place_listContext extends ParserRuleContext {
		public Place_valueContext place_value() {
			return getRuleContext(Place_valueContext.class,0);
		}
		public List<Place_listContext> place_list() {
			return getRuleContexts(Place_listContext.class);
		}
		public Place_listContext place_list(int i) {
			return getRuleContext(Place_listContext.class,i);
		}
		public Place_listContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_place_list; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof JSPNLListener ) ((JSPNLListener)listener).enterPlace_list(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof JSPNLListener ) ((JSPNLListener)listener).exitPlace_list(this);
		}
	}

	public final Place_listContext place_list() throws RecognitionException {
		Place_listContext _localctx = new Place_listContext(_ctx, getState());
		enterRule(_localctx, 30, RULE_place_list);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(201);
			place_value();
			setState(206);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,12,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					{
					{
					setState(202);
					match(T__11);
					setState(203);
					place_list();
					}
					} 
				}
				setState(208);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,12,_ctx);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Place_valueContext extends ParserRuleContext {
		public Ntoken_expressionContext ntoken_expression() {
			return getRuleContext(Ntoken_expressionContext.class,0);
		}
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public Place_valueContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_place_value; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof JSPNLListener ) ((JSPNLListener)listener).enterPlace_value(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof JSPNLListener ) ((JSPNLListener)listener).exitPlace_value(this);
		}
	}

	public final Place_valueContext place_value() throws RecognitionException {
		Place_valueContext _localctx = new Place_valueContext(_ctx, getState());
		enterRule(_localctx, 32, RULE_place_value);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(209);
			ntoken_expression();
			setState(210);
			match(T__31);
			setState(211);
			expression(0);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public boolean sempred(RuleContext _localctx, int ruleIndex, int predIndex) {
		switch (ruleIndex) {
		case 8:
			return expression_sempred((ExpressionContext)_localctx, predIndex);
		}
		return true;
	}
	private boolean expression_sempred(ExpressionContext _localctx, int predIndex) {
		switch (predIndex) {
		case 0:
			return precpred(_ctx, 14);
		case 1:
			return precpred(_ctx, 13);
		case 2:
			return precpred(_ctx, 12);
		case 3:
			return precpred(_ctx, 11);
		case 4:
			return precpred(_ctx, 10);
		case 5:
			return precpred(_ctx, 9);
		}
		return true;
	}

	public static final String _serializedATN =
		"\3\u0430\ud6d1\u8206\uad2d\u4417\uaef1\u8d80\uaadd\3+\u00d8\4\2\t\2\4"+
		"\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4\13\t"+
		"\13\4\f\t\f\4\r\t\r\4\16\t\16\4\17\t\17\4\20\t\20\4\21\t\21\4\22\t\22"+
		"\3\2\7\2&\n\2\f\2\16\2)\13\2\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\5"+
		"\3\65\n\3\3\4\3\4\5\49\n\4\3\5\3\5\3\5\3\5\3\5\3\5\5\5A\n\5\3\6\3\6\3"+
		"\6\3\6\3\6\3\6\3\6\3\6\5\6K\n\6\3\7\3\7\3\7\7\7P\n\7\f\7\16\7S\13\7\3"+
		"\b\3\b\3\t\3\t\3\t\3\t\3\t\3\t\3\t\3\t\3\t\3\t\5\ta\n\t\3\n\3\n\3\n\3"+
		"\n\3\n\3\n\3\n\3\n\3\n\3\n\3\n\3\n\3\n\3\n\3\n\3\n\3\n\3\n\3\n\3\n\3\n"+
		"\3\n\3\n\3\n\3\n\3\n\3\n\3\n\3\n\3\n\3\n\3\n\3\n\3\n\3\n\3\n\3\n\5\n\u0088"+
		"\n\n\3\n\3\n\3\n\3\n\3\n\3\n\3\n\3\n\3\n\3\n\3\n\3\n\3\n\3\n\3\n\3\n\3"+
		"\n\3\n\3\n\3\n\3\n\3\n\3\n\3\n\3\n\3\n\3\n\3\n\3\n\3\n\7\n\u00a8\n\n\f"+
		"\n\16\n\u00ab\13\n\3\13\3\13\3\13\3\13\3\13\3\f\3\f\3\f\7\f\u00b5\n\f"+
		"\f\f\16\f\u00b8\13\f\3\r\3\r\3\16\3\16\3\16\3\17\3\17\3\17\3\20\3\20\3"+
		"\20\3\20\3\20\3\20\3\20\3\20\5\20\u00ca\n\20\3\21\3\21\3\21\7\21\u00cf"+
		"\n\21\f\21\16\21\u00d2\13\21\3\22\3\22\3\22\3\22\3\22\2\3\22\23\2\4\6"+
		"\b\n\f\16\20\22\24\26\30\32\34\36 \"\2\t\3\2\3\6\3\2\t\f\3\2\21\23\3\2"+
		"\24\26\3\2\22\23\3\2\27\32\3\2\33\34\u00e2\2\'\3\2\2\2\4\64\3\2\2\2\6"+
		"8\3\2\2\2\b:\3\2\2\2\nB\3\2\2\2\fL\3\2\2\2\16T\3\2\2\2\20`\3\2\2\2\22"+
		"\u0087\3\2\2\2\24\u00ac\3\2\2\2\26\u00b1\3\2\2\2\30\u00b9\3\2\2\2\32\u00bb"+
		"\3\2\2\2\34\u00be\3\2\2\2\36\u00c9\3\2\2\2 \u00cb\3\2\2\2\"\u00d3\3\2"+
		"\2\2$&\5\4\3\2%$\3\2\2\2&)\3\2\2\2\'%\3\2\2\2\'(\3\2\2\2(\3\3\2\2\2)\'"+
		"\3\2\2\2*+\5\6\4\2+,\7(\2\2,\65\3\2\2\2-.\5\20\t\2./\7(\2\2/\65\3\2\2"+
		"\2\60\61\5\22\n\2\61\62\7(\2\2\62\65\3\2\2\2\63\65\7(\2\2\64*\3\2\2\2"+
		"\64-\3\2\2\2\64\60\3\2\2\2\64\63\3\2\2\2\65\5\3\2\2\2\669\5\b\5\2\679"+
		"\5\n\6\28\66\3\2\2\28\67\3\2\2\29\7\3\2\2\2:;\t\2\2\2;@\7$\2\2<=\7\7\2"+
		"\2=>\5\f\7\2>?\7\b\2\2?A\3\2\2\2@<\3\2\2\2@A\3\2\2\2A\t\3\2\2\2BC\t\3"+
		"\2\2CD\7$\2\2DE\7\r\2\2EJ\7$\2\2FG\7\7\2\2GH\5\f\7\2HI\7\b\2\2IK\3\2\2"+
		"\2JF\3\2\2\2JK\3\2\2\2K\13\3\2\2\2LQ\5\16\b\2MN\7\16\2\2NP\5\f\7\2OM\3"+
		"\2\2\2PS\3\2\2\2QO\3\2\2\2QR\3\2\2\2R\r\3\2\2\2SQ\3\2\2\2TU\5\20\t\2U"+
		"\17\3\2\2\2VW\7$\2\2WX\7\17\2\2XY\5\22\n\2YZ\b\t\1\2Za\3\2\2\2[\\\7$\2"+
		"\2\\]\7\20\2\2]^\5\22\n\2^_\b\t\1\2_a\3\2\2\2`V\3\2\2\2`[\3\2\2\2a\21"+
		"\3\2\2\2bc\b\n\1\2cd\t\4\2\2de\5\22\n\21ef\b\n\1\2f\u0088\3\2\2\2gh\7"+
		"\37\2\2hi\7\7\2\2ij\5\22\n\2jk\7\16\2\2kl\5\22\n\2lm\7\16\2\2mn\5\22\n"+
		"\2no\7\b\2\2op\b\n\1\2p\u0088\3\2\2\2qr\5\24\13\2rs\b\n\1\2s\u0088\3\2"+
		"\2\2tu\5\32\16\2uv\b\n\1\2v\u0088\3\2\2\2wx\5\34\17\2xy\b\n\1\2y\u0088"+
		"\3\2\2\2z{\5\36\20\2{|\b\n\1\2|\u0088\3\2\2\2}~\7$\2\2~\u0088\b\n\1\2"+
		"\177\u0080\7\7\2\2\u0080\u0081\5\22\n\2\u0081\u0082\7\b\2\2\u0082\u0083"+
		"\b\n\1\2\u0083\u0088\3\2\2\2\u0084\u0085\5 \21\2\u0085\u0086\b\n\1\2\u0086"+
		"\u0088\3\2\2\2\u0087b\3\2\2\2\u0087g\3\2\2\2\u0087q\3\2\2\2\u0087t\3\2"+
		"\2\2\u0087w\3\2\2\2\u0087z\3\2\2\2\u0087}\3\2\2\2\u0087\177\3\2\2\2\u0087"+
		"\u0084\3\2\2\2\u0088\u00a9\3\2\2\2\u0089\u008a\f\20\2\2\u008a\u008b\t"+
		"\5\2\2\u008b\u008c\5\22\n\21\u008c\u008d\b\n\1\2\u008d\u00a8\3\2\2\2\u008e"+
		"\u008f\f\17\2\2\u008f\u0090\t\6\2\2\u0090\u0091\5\22\n\20\u0091\u0092"+
		"\b\n\1\2\u0092\u00a8\3\2\2\2\u0093\u0094\f\16\2\2\u0094\u0095\t\7\2\2"+
		"\u0095\u0096\5\22\n\17\u0096\u0097\b\n\1\2\u0097\u00a8\3\2\2\2\u0098\u0099"+
		"\f\r\2\2\u0099\u009a\t\b\2\2\u009a\u009b\5\22\n\16\u009b\u009c\b\n\1\2"+
		"\u009c\u00a8\3\2\2\2\u009d\u009e\f\f\2\2\u009e\u009f\7\35\2\2\u009f\u00a0"+
		"\5\22\n\r\u00a0\u00a1\b\n\1\2\u00a1\u00a8\3\2\2\2\u00a2\u00a3\f\13\2\2"+
		"\u00a3\u00a4\7\36\2\2\u00a4\u00a5\5\22\n\f\u00a5\u00a6\b\n\1\2\u00a6\u00a8"+
		"\3\2\2\2\u00a7\u0089\3\2\2\2\u00a7\u008e\3\2\2\2\u00a7\u0093\3\2\2\2\u00a7"+
		"\u0098\3\2\2\2\u00a7\u009d\3\2\2\2\u00a7\u00a2\3\2\2\2\u00a8\u00ab\3\2"+
		"\2\2\u00a9\u00a7\3\2\2\2\u00a9\u00aa\3\2\2\2\u00aa\23\3\2\2\2\u00ab\u00a9"+
		"\3\2\2\2\u00ac\u00ad\7$\2\2\u00ad\u00ae\7\7\2\2\u00ae\u00af\5\26\f\2\u00af"+
		"\u00b0\7\b\2\2\u00b0\25\3\2\2\2\u00b1\u00b6\5\30\r\2\u00b2\u00b3\7\16"+
		"\2\2\u00b3\u00b5\5\26\f\2\u00b4\u00b2\3\2\2\2\u00b5\u00b8\3\2\2\2\u00b6"+
		"\u00b4\3\2\2\2\u00b6\u00b7\3\2\2\2\u00b7\27\3\2\2\2\u00b8\u00b6\3\2\2"+
		"\2\u00b9\u00ba\5\22\n\2\u00ba\31\3\2\2\2\u00bb\u00bc\7 \2\2\u00bc\u00bd"+
		"\7$\2\2\u00bd\33\3\2\2\2\u00be\u00bf\7!\2\2\u00bf\u00c0\7$\2\2\u00c0\35"+
		"\3\2\2\2\u00c1\u00c2\7%\2\2\u00c2\u00ca\b\20\1\2\u00c3\u00c4\7&\2\2\u00c4"+
		"\u00ca\b\20\1\2\u00c5\u00c6\7#\2\2\u00c6\u00ca\b\20\1\2\u00c7\u00c8\7"+
		"\'\2\2\u00c8\u00ca\b\20\1\2\u00c9\u00c1\3\2\2\2\u00c9\u00c3\3\2\2\2\u00c9"+
		"\u00c5\3\2\2\2\u00c9\u00c7\3\2\2\2\u00ca\37\3\2\2\2\u00cb\u00d0\5\"\22"+
		"\2\u00cc\u00cd\7\16\2\2\u00cd\u00cf\5 \21\2\u00ce\u00cc\3\2\2\2\u00cf"+
		"\u00d2\3\2\2\2\u00d0\u00ce\3\2\2\2\u00d0\u00d1\3\2\2\2\u00d1!\3\2\2\2"+
		"\u00d2\u00d0\3\2\2\2\u00d3\u00d4\5\32\16\2\u00d4\u00d5\7\"\2\2\u00d5\u00d6"+
		"\5\22\n\2\u00d6#\3\2\2\2\17\'\648@JQ`\u0087\u00a7\u00a9\u00b6\u00c9\u00d0";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}