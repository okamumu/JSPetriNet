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
		RULE_prog = 0, RULE_statement = 1, RULE_simple_block = 2, RULE_declaration = 3, 
		RULE_node_declaration = 4, RULE_arc_declaration = 5, RULE_option_list = 6, 
		RULE_option_value = 7, RULE_simple = 8, RULE_assign_expression = 9, RULE_expression = 10, 
		RULE_function_expression = 11, RULE_arg_list = 12, RULE_arg_value = 13, 
		RULE_ntoken_expression = 14, RULE_enable_expression = 15, RULE_literal_expression = 16;
	public static final String[] ruleNames = {
		"prog", "statement", "simple_block", "declaration", "node_declaration", 
		"arc_declaration", "option_list", "option_value", "simple", "assign_expression", 
		"expression", "function_expression", "arg_list", "arg_value", "ntoken_expression", 
		"enable_expression", "literal_expression"
	};

	private static final String[] _LITERAL_NAMES = {
		null, "'{'", "'}'", "'place'", "'('", "')'", "'trans'", "'arc'", "'iarc'", 
		"'oarc'", "'harc'", "'to'", "','", "'='", "':='", "'!'", "'+'", "'-'", 
		"'*'", "'/'", "'div'", "'mod'", "'<'", "'<='", "'>'", "'>='", "'=='", 
		"'!='", "'&&'", "'||'", "'ifelse'", "'#'", "'?'"
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
		public List<TerminalNode> NEWLINE() { return getTokens(JSPNLParser.NEWLINE); }
		public TerminalNode NEWLINE(int i) {
			return getToken(JSPNLParser.NEWLINE, i);
		}
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
			setState(40);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__2) | (1L << T__3) | (1L << T__5) | (1L << T__6) | (1L << T__7) | (1L << T__8) | (1L << T__9) | (1L << T__14) | (1L << T__15) | (1L << T__16) | (1L << T__29) | (1L << T__30) | (1L << T__31) | (1L << LOGICAL) | (1L << ID) | (1L << INT) | (1L << FLOAT) | (1L << STRING) | (1L << NEWLINE))) != 0)) {
				{
				{
				setState(35);
				_la = _input.LA(1);
				if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__2) | (1L << T__3) | (1L << T__5) | (1L << T__6) | (1L << T__7) | (1L << T__8) | (1L << T__9) | (1L << T__14) | (1L << T__15) | (1L << T__16) | (1L << T__29) | (1L << T__30) | (1L << T__31) | (1L << LOGICAL) | (1L << ID) | (1L << INT) | (1L << FLOAT) | (1L << STRING))) != 0)) {
					{
					setState(34);
					statement();
					}
				}

				setState(37);
				match(NEWLINE);
				}
				}
				setState(42);
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
		public SimpleContext simple() {
			return getRuleContext(SimpleContext.class,0);
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
			setState(45);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,2,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(43);
				declaration();
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(44);
				simple();
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

	public static class Simple_blockContext extends ParserRuleContext {
		public List<TerminalNode> NEWLINE() { return getTokens(JSPNLParser.NEWLINE); }
		public TerminalNode NEWLINE(int i) {
			return getToken(JSPNLParser.NEWLINE, i);
		}
		public List<SimpleContext> simple() {
			return getRuleContexts(SimpleContext.class);
		}
		public SimpleContext simple(int i) {
			return getRuleContext(SimpleContext.class,i);
		}
		public Simple_blockContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_simple_block; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof JSPNLListener ) ((JSPNLListener)listener).enterSimple_block(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof JSPNLListener ) ((JSPNLListener)listener).exitSimple_block(this);
		}
	}

	public final Simple_blockContext simple_block() throws RecognitionException {
		Simple_blockContext _localctx = new Simple_blockContext(_ctx, getState());
		enterRule(_localctx, 4, RULE_simple_block);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(50);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==NEWLINE) {
				{
				{
				setState(47);
				match(NEWLINE);
				}
				}
				setState(52);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(53);
			match(T__0);
			setState(55);
			_la = _input.LA(1);
			if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__3) | (1L << T__14) | (1L << T__15) | (1L << T__16) | (1L << T__29) | (1L << T__30) | (1L << T__31) | (1L << LOGICAL) | (1L << ID) | (1L << INT) | (1L << FLOAT) | (1L << STRING))) != 0)) {
				{
				setState(54);
				simple();
				}
			}

			setState(63);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==NEWLINE) {
				{
				{
				setState(57);
				match(NEWLINE);
				setState(59);
				_la = _input.LA(1);
				if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__3) | (1L << T__14) | (1L << T__15) | (1L << T__16) | (1L << T__29) | (1L << T__30) | (1L << T__31) | (1L << LOGICAL) | (1L << ID) | (1L << INT) | (1L << FLOAT) | (1L << STRING))) != 0)) {
					{
					setState(58);
					simple();
					}
				}

				}
				}
				setState(65);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(66);
			match(T__1);
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
		enterRule(_localctx, 6, RULE_declaration);
		try {
			setState(70);
			switch (_input.LA(1)) {
			case T__2:
			case T__5:
			case ID:
				enterOuterAlt(_localctx, 1);
				{
				setState(68);
				node_declaration();
				}
				break;
			case T__6:
			case T__7:
			case T__8:
			case T__9:
				enterOuterAlt(_localctx, 2);
				{
				setState(69);
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
		public Token node;
		public Token id;
		public List<TerminalNode> ID() { return getTokens(JSPNLParser.ID); }
		public TerminalNode ID(int i) {
			return getToken(JSPNLParser.ID, i);
		}
		public Option_listContext option_list() {
			return getRuleContext(Option_listContext.class,0);
		}
		public Simple_blockContext simple_block() {
			return getRuleContext(Simple_blockContext.class,0);
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
		enterRule(_localctx, 8, RULE_node_declaration);
		int _la;
		try {
			setState(102);
			switch (_input.LA(1)) {
			case T__2:
				enterOuterAlt(_localctx, 1);
				{
				setState(72);
				((Node_declarationContext)_localctx).node = match(T__2);
				setState(73);
				((Node_declarationContext)_localctx).id = match(ID);
				setState(78);
				_la = _input.LA(1);
				if (_la==T__3) {
					{
					setState(74);
					match(T__3);
					setState(75);
					option_list();
					setState(76);
					match(T__4);
					}
				}

				}
				break;
			case T__5:
				enterOuterAlt(_localctx, 2);
				{
				setState(80);
				((Node_declarationContext)_localctx).node = match(T__5);
				setState(81);
				((Node_declarationContext)_localctx).id = match(ID);
				setState(86);
				_la = _input.LA(1);
				if (_la==T__3) {
					{
					setState(82);
					match(T__3);
					setState(83);
					option_list();
					setState(84);
					match(T__4);
					}
				}

				setState(89);
				_errHandler.sync(this);
				switch ( getInterpreter().adaptivePredict(_input,10,_ctx) ) {
				case 1:
					{
					setState(88);
					simple_block();
					}
					break;
				}
				}
				break;
			case ID:
				enterOuterAlt(_localctx, 3);
				{
				setState(91);
				((Node_declarationContext)_localctx).node = match(ID);
				setState(92);
				((Node_declarationContext)_localctx).id = match(ID);
				setState(97);
				_la = _input.LA(1);
				if (_la==T__3) {
					{
					setState(93);
					match(T__3);
					setState(94);
					option_list();
					setState(95);
					match(T__4);
					}
				}

				setState(100);
				_errHandler.sync(this);
				switch ( getInterpreter().adaptivePredict(_input,12,_ctx) ) {
				case 1:
					{
					setState(99);
					simple_block();
					}
					break;
				}
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
		enterRule(_localctx, 10, RULE_arc_declaration);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(104);
			((Arc_declarationContext)_localctx).type = _input.LT(1);
			_la = _input.LA(1);
			if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__6) | (1L << T__7) | (1L << T__8) | (1L << T__9))) != 0)) ) {
				((Arc_declarationContext)_localctx).type = (Token)_errHandler.recoverInline(this);
			} else {
				consume();
			}
			setState(105);
			((Arc_declarationContext)_localctx).srcName = match(ID);
			setState(106);
			match(T__10);
			setState(107);
			((Arc_declarationContext)_localctx).destName = match(ID);
			setState(112);
			_la = _input.LA(1);
			if (_la==T__3) {
				{
				setState(108);
				match(T__3);
				setState(109);
				option_list();
				setState(110);
				match(T__4);
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
		enterRule(_localctx, 12, RULE_option_list);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(114);
			option_value();
			setState(119);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,15,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					{
					{
					setState(115);
					match(T__11);
					setState(116);
					option_list();
					}
					} 
				}
				setState(121);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,15,_ctx);
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
		enterRule(_localctx, 14, RULE_option_value);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(122);
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

	public static class SimpleContext extends ParserRuleContext {
		public Assign_expressionContext assign_expression() {
			return getRuleContext(Assign_expressionContext.class,0);
		}
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public SimpleContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_simple; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof JSPNLListener ) ((JSPNLListener)listener).enterSimple(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof JSPNLListener ) ((JSPNLListener)listener).exitSimple(this);
		}
	}

	public final SimpleContext simple() throws RecognitionException {
		SimpleContext _localctx = new SimpleContext(_ctx, getState());
		enterRule(_localctx, 16, RULE_simple);
		try {
			setState(126);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,16,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(124);
				assign_expression();
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(125);
				expression(0);
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

	public static class Assign_expressionContext extends ParserRuleContext {
		public int type;
		public Token id;
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public TerminalNode ID() { return getToken(JSPNLParser.ID, 0); }
		public Ntoken_expressionContext ntoken_expression() {
			return getRuleContext(Ntoken_expressionContext.class,0);
		}
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
		enterRule(_localctx, 18, RULE_assign_expression);
		try {
			setState(143);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,17,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(128);
				((Assign_expressionContext)_localctx).id = match(ID);
				setState(129);
				match(T__12);
				setState(130);
				expression(0);
				 ((Assign_expressionContext)_localctx).type =  1; 
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(133);
				((Assign_expressionContext)_localctx).id = match(ID);
				setState(134);
				match(T__13);
				setState(135);
				expression(0);
				 ((Assign_expressionContext)_localctx).type =  2; 
				}
				break;
			case 3:
				enterOuterAlt(_localctx, 3);
				{
				setState(138);
				ntoken_expression();
				setState(139);
				match(T__12);
				setState(140);
				expression(0);
				 ((Assign_expressionContext)_localctx).type =  3; 
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
		int _startState = 20;
		enterRecursionRule(_localctx, 20, RULE_expression, _p);
		int _la;
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(179);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,18,_ctx) ) {
			case 1:
				{
				setState(146);
				((ExpressionContext)_localctx).op = _input.LT(1);
				_la = _input.LA(1);
				if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__14) | (1L << T__15) | (1L << T__16))) != 0)) ) {
					((ExpressionContext)_localctx).op = (Token)_errHandler.recoverInline(this);
				} else {
					consume();
				}
				setState(147);
				expression(14);
				 ((ExpressionContext)_localctx).type =  1; 
				}
				break;
			case 2:
				{
				setState(150);
				((ExpressionContext)_localctx).op = match(T__29);
				setState(151);
				match(T__3);
				setState(152);
				expression(0);
				setState(153);
				match(T__11);
				setState(154);
				expression(0);
				setState(155);
				match(T__11);
				setState(156);
				expression(0);
				setState(157);
				match(T__4);
				 ((ExpressionContext)_localctx).type =  8; 
				}
				break;
			case 3:
				{
				setState(160);
				function_expression();
				 ((ExpressionContext)_localctx).type =  9; 
				}
				break;
			case 4:
				{
				setState(163);
				ntoken_expression();
				 ((ExpressionContext)_localctx).type =  10; 
				}
				break;
			case 5:
				{
				setState(166);
				enable_expression();
				 ((ExpressionContext)_localctx).type =  14; 
				}
				break;
			case 6:
				{
				setState(169);
				literal_expression();
				 ((ExpressionContext)_localctx).type =  11; 
				}
				break;
			case 7:
				{
				setState(172);
				((ExpressionContext)_localctx).id = match(ID);
				 ((ExpressionContext)_localctx).type =  12; 
				}
				break;
			case 8:
				{
				setState(174);
				match(T__3);
				setState(175);
				expression(0);
				setState(176);
				match(T__4);
				 ((ExpressionContext)_localctx).type =  13; 
				}
				break;
			}
			_ctx.stop = _input.LT(-1);
			setState(213);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,20,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					if ( _parseListeners!=null ) triggerExitRuleEvent();
					_prevctx = _localctx;
					{
					setState(211);
					_errHandler.sync(this);
					switch ( getInterpreter().adaptivePredict(_input,19,_ctx) ) {
					case 1:
						{
						_localctx = new ExpressionContext(_parentctx, _parentState);
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(181);
						if (!(precpred(_ctx, 13))) throw new FailedPredicateException(this, "precpred(_ctx, 13)");
						setState(182);
						((ExpressionContext)_localctx).op = _input.LT(1);
						_la = _input.LA(1);
						if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__17) | (1L << T__18) | (1L << T__19) | (1L << T__20))) != 0)) ) {
							((ExpressionContext)_localctx).op = (Token)_errHandler.recoverInline(this);
						} else {
							consume();
						}
						setState(183);
						expression(14);
						 ((ExpressionContext)_localctx).type =  2; 
						}
						break;
					case 2:
						{
						_localctx = new ExpressionContext(_parentctx, _parentState);
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(186);
						if (!(precpred(_ctx, 12))) throw new FailedPredicateException(this, "precpred(_ctx, 12)");
						setState(187);
						((ExpressionContext)_localctx).op = _input.LT(1);
						_la = _input.LA(1);
						if ( !(_la==T__15 || _la==T__16) ) {
							((ExpressionContext)_localctx).op = (Token)_errHandler.recoverInline(this);
						} else {
							consume();
						}
						setState(188);
						expression(13);
						 ((ExpressionContext)_localctx).type =  3; 
						}
						break;
					case 3:
						{
						_localctx = new ExpressionContext(_parentctx, _parentState);
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(191);
						if (!(precpred(_ctx, 11))) throw new FailedPredicateException(this, "precpred(_ctx, 11)");
						setState(192);
						((ExpressionContext)_localctx).op = _input.LT(1);
						_la = _input.LA(1);
						if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__21) | (1L << T__22) | (1L << T__23) | (1L << T__24))) != 0)) ) {
							((ExpressionContext)_localctx).op = (Token)_errHandler.recoverInline(this);
						} else {
							consume();
						}
						setState(193);
						expression(12);
						 ((ExpressionContext)_localctx).type =  4; 
						}
						break;
					case 4:
						{
						_localctx = new ExpressionContext(_parentctx, _parentState);
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(196);
						if (!(precpred(_ctx, 10))) throw new FailedPredicateException(this, "precpred(_ctx, 10)");
						setState(197);
						((ExpressionContext)_localctx).op = _input.LT(1);
						_la = _input.LA(1);
						if ( !(_la==T__25 || _la==T__26) ) {
							((ExpressionContext)_localctx).op = (Token)_errHandler.recoverInline(this);
						} else {
							consume();
						}
						setState(198);
						expression(11);
						 ((ExpressionContext)_localctx).type =  5; 
						}
						break;
					case 5:
						{
						_localctx = new ExpressionContext(_parentctx, _parentState);
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(201);
						if (!(precpred(_ctx, 9))) throw new FailedPredicateException(this, "precpred(_ctx, 9)");
						setState(202);
						((ExpressionContext)_localctx).op = match(T__27);
						setState(203);
						expression(10);
						 ((ExpressionContext)_localctx).type =  6; 
						}
						break;
					case 6:
						{
						_localctx = new ExpressionContext(_parentctx, _parentState);
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(206);
						if (!(precpred(_ctx, 8))) throw new FailedPredicateException(this, "precpred(_ctx, 8)");
						setState(207);
						((ExpressionContext)_localctx).op = match(T__28);
						setState(208);
						expression(9);
						 ((ExpressionContext)_localctx).type =  7; 
						}
						break;
					}
					} 
				}
				setState(215);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,20,_ctx);
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
		enterRule(_localctx, 22, RULE_function_expression);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(216);
			((Function_expressionContext)_localctx).id = match(ID);
			setState(217);
			match(T__3);
			setState(218);
			arg_list();
			setState(219);
			match(T__4);
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
		enterRule(_localctx, 24, RULE_arg_list);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(221);
			arg_value();
			setState(226);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,21,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					{
					{
					setState(222);
					match(T__11);
					setState(223);
					arg_list();
					}
					} 
				}
				setState(228);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,21,_ctx);
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
		enterRule(_localctx, 26, RULE_arg_value);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(229);
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
		enterRule(_localctx, 28, RULE_ntoken_expression);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(231);
			match(T__30);
			setState(232);
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
		enterRule(_localctx, 30, RULE_enable_expression);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(234);
			match(T__31);
			setState(235);
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
		enterRule(_localctx, 32, RULE_literal_expression);
		try {
			setState(245);
			switch (_input.LA(1)) {
			case INT:
				enterOuterAlt(_localctx, 1);
				{
				setState(237);
				((Literal_expressionContext)_localctx).val = match(INT);
				 ((Literal_expressionContext)_localctx).type =  1; 
				}
				break;
			case FLOAT:
				enterOuterAlt(_localctx, 2);
				{
				setState(239);
				((Literal_expressionContext)_localctx).val = match(FLOAT);
				 ((Literal_expressionContext)_localctx).type =  2; 
				}
				break;
			case LOGICAL:
				enterOuterAlt(_localctx, 3);
				{
				setState(241);
				((Literal_expressionContext)_localctx).val = match(LOGICAL);
				 ((Literal_expressionContext)_localctx).type =  3; 
				}
				break;
			case STRING:
				enterOuterAlt(_localctx, 4);
				{
				setState(243);
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

	public boolean sempred(RuleContext _localctx, int ruleIndex, int predIndex) {
		switch (ruleIndex) {
		case 10:
			return expression_sempred((ExpressionContext)_localctx, predIndex);
		}
		return true;
	}
	private boolean expression_sempred(ExpressionContext _localctx, int predIndex) {
		switch (predIndex) {
		case 0:
			return precpred(_ctx, 13);
		case 1:
			return precpred(_ctx, 12);
		case 2:
			return precpred(_ctx, 11);
		case 3:
			return precpred(_ctx, 10);
		case 4:
			return precpred(_ctx, 9);
		case 5:
			return precpred(_ctx, 8);
		}
		return true;
	}

	public static final String _serializedATN =
		"\3\u0430\ud6d1\u8206\uad2d\u4417\uaef1\u8d80\uaadd\3+\u00fa\4\2\t\2\4"+
		"\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4\13\t"+
		"\13\4\f\t\f\4\r\t\r\4\16\t\16\4\17\t\17\4\20\t\20\4\21\t\21\4\22\t\22"+
		"\3\2\5\2&\n\2\3\2\7\2)\n\2\f\2\16\2,\13\2\3\3\3\3\5\3\60\n\3\3\4\7\4\63"+
		"\n\4\f\4\16\4\66\13\4\3\4\3\4\5\4:\n\4\3\4\3\4\5\4>\n\4\7\4@\n\4\f\4\16"+
		"\4C\13\4\3\4\3\4\3\5\3\5\5\5I\n\5\3\6\3\6\3\6\3\6\3\6\3\6\5\6Q\n\6\3\6"+
		"\3\6\3\6\3\6\3\6\3\6\5\6Y\n\6\3\6\5\6\\\n\6\3\6\3\6\3\6\3\6\3\6\3\6\5"+
		"\6d\n\6\3\6\5\6g\n\6\5\6i\n\6\3\7\3\7\3\7\3\7\3\7\3\7\3\7\3\7\5\7s\n\7"+
		"\3\b\3\b\3\b\7\bx\n\b\f\b\16\b{\13\b\3\t\3\t\3\n\3\n\5\n\u0081\n\n\3\13"+
		"\3\13\3\13\3\13\3\13\3\13\3\13\3\13\3\13\3\13\3\13\3\13\3\13\3\13\3\13"+
		"\5\13\u0092\n\13\3\f\3\f\3\f\3\f\3\f\3\f\3\f\3\f\3\f\3\f\3\f\3\f\3\f\3"+
		"\f\3\f\3\f\3\f\3\f\3\f\3\f\3\f\3\f\3\f\3\f\3\f\3\f\3\f\3\f\3\f\3\f\3\f"+
		"\3\f\3\f\3\f\5\f\u00b6\n\f\3\f\3\f\3\f\3\f\3\f\3\f\3\f\3\f\3\f\3\f\3\f"+
		"\3\f\3\f\3\f\3\f\3\f\3\f\3\f\3\f\3\f\3\f\3\f\3\f\3\f\3\f\3\f\3\f\3\f\3"+
		"\f\3\f\7\f\u00d6\n\f\f\f\16\f\u00d9\13\f\3\r\3\r\3\r\3\r\3\r\3\16\3\16"+
		"\3\16\7\16\u00e3\n\16\f\16\16\16\u00e6\13\16\3\17\3\17\3\20\3\20\3\20"+
		"\3\21\3\21\3\21\3\22\3\22\3\22\3\22\3\22\3\22\3\22\3\22\5\22\u00f8\n\22"+
		"\3\22\2\3\26\23\2\4\6\b\n\f\16\20\22\24\26\30\32\34\36 \"\2\b\3\2\t\f"+
		"\3\2\21\23\3\2\24\27\3\2\22\23\3\2\30\33\3\2\34\35\u010d\2*\3\2\2\2\4"+
		"/\3\2\2\2\6\64\3\2\2\2\bH\3\2\2\2\nh\3\2\2\2\fj\3\2\2\2\16t\3\2\2\2\20"+
		"|\3\2\2\2\22\u0080\3\2\2\2\24\u0091\3\2\2\2\26\u00b5\3\2\2\2\30\u00da"+
		"\3\2\2\2\32\u00df\3\2\2\2\34\u00e7\3\2\2\2\36\u00e9\3\2\2\2 \u00ec\3\2"+
		"\2\2\"\u00f7\3\2\2\2$&\5\4\3\2%$\3\2\2\2%&\3\2\2\2&\'\3\2\2\2\')\7(\2"+
		"\2(%\3\2\2\2),\3\2\2\2*(\3\2\2\2*+\3\2\2\2+\3\3\2\2\2,*\3\2\2\2-\60\5"+
		"\b\5\2.\60\5\22\n\2/-\3\2\2\2/.\3\2\2\2\60\5\3\2\2\2\61\63\7(\2\2\62\61"+
		"\3\2\2\2\63\66\3\2\2\2\64\62\3\2\2\2\64\65\3\2\2\2\65\67\3\2\2\2\66\64"+
		"\3\2\2\2\679\7\3\2\28:\5\22\n\298\3\2\2\29:\3\2\2\2:A\3\2\2\2;=\7(\2\2"+
		"<>\5\22\n\2=<\3\2\2\2=>\3\2\2\2>@\3\2\2\2?;\3\2\2\2@C\3\2\2\2A?\3\2\2"+
		"\2AB\3\2\2\2BD\3\2\2\2CA\3\2\2\2DE\7\4\2\2E\7\3\2\2\2FI\5\n\6\2GI\5\f"+
		"\7\2HF\3\2\2\2HG\3\2\2\2I\t\3\2\2\2JK\7\5\2\2KP\7$\2\2LM\7\6\2\2MN\5\16"+
		"\b\2NO\7\7\2\2OQ\3\2\2\2PL\3\2\2\2PQ\3\2\2\2Qi\3\2\2\2RS\7\b\2\2SX\7$"+
		"\2\2TU\7\6\2\2UV\5\16\b\2VW\7\7\2\2WY\3\2\2\2XT\3\2\2\2XY\3\2\2\2Y[\3"+
		"\2\2\2Z\\\5\6\4\2[Z\3\2\2\2[\\\3\2\2\2\\i\3\2\2\2]^\7$\2\2^c\7$\2\2_`"+
		"\7\6\2\2`a\5\16\b\2ab\7\7\2\2bd\3\2\2\2c_\3\2\2\2cd\3\2\2\2df\3\2\2\2"+
		"eg\5\6\4\2fe\3\2\2\2fg\3\2\2\2gi\3\2\2\2hJ\3\2\2\2hR\3\2\2\2h]\3\2\2\2"+
		"i\13\3\2\2\2jk\t\2\2\2kl\7$\2\2lm\7\r\2\2mr\7$\2\2no\7\6\2\2op\5\16\b"+
		"\2pq\7\7\2\2qs\3\2\2\2rn\3\2\2\2rs\3\2\2\2s\r\3\2\2\2ty\5\20\t\2uv\7\16"+
		"\2\2vx\5\16\b\2wu\3\2\2\2x{\3\2\2\2yw\3\2\2\2yz\3\2\2\2z\17\3\2\2\2{y"+
		"\3\2\2\2|}\5\24\13\2}\21\3\2\2\2~\u0081\5\24\13\2\177\u0081\5\26\f\2\u0080"+
		"~\3\2\2\2\u0080\177\3\2\2\2\u0081\23\3\2\2\2\u0082\u0083\7$\2\2\u0083"+
		"\u0084\7\17\2\2\u0084\u0085\5\26\f\2\u0085\u0086\b\13\1\2\u0086\u0092"+
		"\3\2\2\2\u0087\u0088\7$\2\2\u0088\u0089\7\20\2\2\u0089\u008a\5\26\f\2"+
		"\u008a\u008b\b\13\1\2\u008b\u0092\3\2\2\2\u008c\u008d\5\36\20\2\u008d"+
		"\u008e\7\17\2\2\u008e\u008f\5\26\f\2\u008f\u0090\b\13\1\2\u0090\u0092"+
		"\3\2\2\2\u0091\u0082\3\2\2\2\u0091\u0087\3\2\2\2\u0091\u008c\3\2\2\2\u0092"+
		"\25\3\2\2\2\u0093\u0094\b\f\1\2\u0094\u0095\t\3\2\2\u0095\u0096\5\26\f"+
		"\20\u0096\u0097\b\f\1\2\u0097\u00b6\3\2\2\2\u0098\u0099\7 \2\2\u0099\u009a"+
		"\7\6\2\2\u009a\u009b\5\26\f\2\u009b\u009c\7\16\2\2\u009c\u009d\5\26\f"+
		"\2\u009d\u009e\7\16\2\2\u009e\u009f\5\26\f\2\u009f\u00a0\7\7\2\2\u00a0"+
		"\u00a1\b\f\1\2\u00a1\u00b6\3\2\2\2\u00a2\u00a3\5\30\r\2\u00a3\u00a4\b"+
		"\f\1\2\u00a4\u00b6\3\2\2\2\u00a5\u00a6\5\36\20\2\u00a6\u00a7\b\f\1\2\u00a7"+
		"\u00b6\3\2\2\2\u00a8\u00a9\5 \21\2\u00a9\u00aa\b\f\1\2\u00aa\u00b6\3\2"+
		"\2\2\u00ab\u00ac\5\"\22\2\u00ac\u00ad\b\f\1\2\u00ad\u00b6\3\2\2\2\u00ae"+
		"\u00af\7$\2\2\u00af\u00b6\b\f\1\2\u00b0\u00b1\7\6\2\2\u00b1\u00b2\5\26"+
		"\f\2\u00b2\u00b3\7\7\2\2\u00b3\u00b4\b\f\1\2\u00b4\u00b6\3\2\2\2\u00b5"+
		"\u0093\3\2\2\2\u00b5\u0098\3\2\2\2\u00b5\u00a2\3\2\2\2\u00b5\u00a5\3\2"+
		"\2\2\u00b5\u00a8\3\2\2\2\u00b5\u00ab\3\2\2\2\u00b5\u00ae\3\2\2\2\u00b5"+
		"\u00b0\3\2\2\2\u00b6\u00d7\3\2\2\2\u00b7\u00b8\f\17\2\2\u00b8\u00b9\t"+
		"\4\2\2\u00b9\u00ba\5\26\f\20\u00ba\u00bb\b\f\1\2\u00bb\u00d6\3\2\2\2\u00bc"+
		"\u00bd\f\16\2\2\u00bd\u00be\t\5\2\2\u00be\u00bf\5\26\f\17\u00bf\u00c0"+
		"\b\f\1\2\u00c0\u00d6\3\2\2\2\u00c1\u00c2\f\r\2\2\u00c2\u00c3\t\6\2\2\u00c3"+
		"\u00c4\5\26\f\16\u00c4\u00c5\b\f\1\2\u00c5\u00d6\3\2\2\2\u00c6\u00c7\f"+
		"\f\2\2\u00c7\u00c8\t\7\2\2\u00c8\u00c9\5\26\f\r\u00c9\u00ca\b\f\1\2\u00ca"+
		"\u00d6\3\2\2\2\u00cb\u00cc\f\13\2\2\u00cc\u00cd\7\36\2\2\u00cd\u00ce\5"+
		"\26\f\f\u00ce\u00cf\b\f\1\2\u00cf\u00d6\3\2\2\2\u00d0\u00d1\f\n\2\2\u00d1"+
		"\u00d2\7\37\2\2\u00d2\u00d3\5\26\f\13\u00d3\u00d4\b\f\1\2\u00d4\u00d6"+
		"\3\2\2\2\u00d5\u00b7\3\2\2\2\u00d5\u00bc\3\2\2\2\u00d5\u00c1\3\2\2\2\u00d5"+
		"\u00c6\3\2\2\2\u00d5\u00cb\3\2\2\2\u00d5\u00d0\3\2\2\2\u00d6\u00d9\3\2"+
		"\2\2\u00d7\u00d5\3\2\2\2\u00d7\u00d8\3\2\2\2\u00d8\27\3\2\2\2\u00d9\u00d7"+
		"\3\2\2\2\u00da\u00db\7$\2\2\u00db\u00dc\7\6\2\2\u00dc\u00dd\5\32\16\2"+
		"\u00dd\u00de\7\7\2\2\u00de\31\3\2\2\2\u00df\u00e4\5\34\17\2\u00e0\u00e1"+
		"\7\16\2\2\u00e1\u00e3\5\32\16\2\u00e2\u00e0\3\2\2\2\u00e3\u00e6\3\2\2"+
		"\2\u00e4\u00e2\3\2\2\2\u00e4\u00e5\3\2\2\2\u00e5\33\3\2\2\2\u00e6\u00e4"+
		"\3\2\2\2\u00e7\u00e8\5\26\f\2\u00e8\35\3\2\2\2\u00e9\u00ea\7!\2\2\u00ea"+
		"\u00eb\7$\2\2\u00eb\37\3\2\2\2\u00ec\u00ed\7\"\2\2\u00ed\u00ee\7$\2\2"+
		"\u00ee!\3\2\2\2\u00ef\u00f0\7%\2\2\u00f0\u00f8\b\22\1\2\u00f1\u00f2\7"+
		"&\2\2\u00f2\u00f8\b\22\1\2\u00f3\u00f4\7#\2\2\u00f4\u00f8\b\22\1\2\u00f5"+
		"\u00f6\7\'\2\2\u00f6\u00f8\b\22\1\2\u00f7\u00ef\3\2\2\2\u00f7\u00f1\3"+
		"\2\2\2\u00f7\u00f3\3\2\2\2\u00f7\u00f5\3\2\2\2\u00f8#\3\2\2\2\31%*/\64"+
		"9=AHPX[cfhry\u0080\u0091\u00b5\u00d5\u00d7\u00e4\u00f7";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}