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
		T__31=32, T__32=33, LOGICAL=34, ID=35, INT=36, FLOAT=37, STRING=38, NEWLINE=39, 
		WS=40, LINE_COMMENT=41, BLOCK_COMMENT=42;
	public static final int
		RULE_prog = 0, RULE_statement = 1, RULE_simple_block = 2, RULE_declaration = 3, 
		RULE_node_declaration = 4, RULE_arc_declaration = 5, RULE_assert_declaration = 6, 
		RULE_option_list = 7, RULE_option_value = 8, RULE_simple = 9, RULE_assign_expression = 10, 
		RULE_expression = 11, RULE_function_expression = 12, RULE_arg_list = 13, 
		RULE_arg_value = 14, RULE_ntoken_expression = 15, RULE_enable_expression = 16, 
		RULE_literal_expression = 17;
	public static final String[] ruleNames = {
		"prog", "statement", "simple_block", "declaration", "node_declaration", 
		"arc_declaration", "assert_declaration", "option_list", "option_value", 
		"simple", "assign_expression", "expression", "function_expression", "arg_list", 
		"arg_value", "ntoken_expression", "enable_expression", "literal_expression"
	};

	private static final String[] _LITERAL_NAMES = {
		null, "'{'", "'}'", "'place'", "'('", "')'", "'trans'", "'arc'", "'iarc'", 
		"'oarc'", "'harc'", "'to'", "'assert'", "','", "'='", "':='", "'!'", "'+'", 
		"'-'", "'*'", "'/'", "'div'", "'mod'", "'<'", "'<='", "'>'", "'>='", "'=='", 
		"'!='", "'&&'", "'||'", "'ifelse'", "'#'", "'?'"
	};
	private static final String[] _SYMBOLIC_NAMES = {
		null, null, null, null, null, null, null, null, null, null, null, null, 
		null, null, null, null, null, null, null, null, null, null, null, null, 
		null, null, null, null, null, null, null, null, null, null, "LOGICAL", 
		"ID", "INT", "FLOAT", "STRING", "NEWLINE", "WS", "LINE_COMMENT", "BLOCK_COMMENT"
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
			setState(42);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__2) | (1L << T__3) | (1L << T__5) | (1L << T__6) | (1L << T__7) | (1L << T__8) | (1L << T__9) | (1L << T__11) | (1L << T__15) | (1L << T__16) | (1L << T__17) | (1L << T__30) | (1L << T__31) | (1L << T__32) | (1L << LOGICAL) | (1L << ID) | (1L << INT) | (1L << FLOAT) | (1L << STRING) | (1L << NEWLINE))) != 0)) {
				{
				{
				setState(37);
				_la = _input.LA(1);
				if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__2) | (1L << T__3) | (1L << T__5) | (1L << T__6) | (1L << T__7) | (1L << T__8) | (1L << T__9) | (1L << T__11) | (1L << T__15) | (1L << T__16) | (1L << T__17) | (1L << T__30) | (1L << T__31) | (1L << T__32) | (1L << LOGICAL) | (1L << ID) | (1L << INT) | (1L << FLOAT) | (1L << STRING))) != 0)) {
					{
					setState(36);
					statement();
					}
				}

				setState(39);
				match(NEWLINE);
				}
				}
				setState(44);
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
			setState(47);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,2,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(45);
				declaration();
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(46);
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
			setState(52);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==NEWLINE) {
				{
				{
				setState(49);
				match(NEWLINE);
				}
				}
				setState(54);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(55);
			match(T__0);
			setState(57);
			_la = _input.LA(1);
			if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__3) | (1L << T__15) | (1L << T__16) | (1L << T__17) | (1L << T__30) | (1L << T__31) | (1L << T__32) | (1L << LOGICAL) | (1L << ID) | (1L << INT) | (1L << FLOAT) | (1L << STRING))) != 0)) {
				{
				setState(56);
				simple();
				}
			}

			setState(65);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==NEWLINE) {
				{
				{
				setState(59);
				match(NEWLINE);
				setState(61);
				_la = _input.LA(1);
				if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__3) | (1L << T__15) | (1L << T__16) | (1L << T__17) | (1L << T__30) | (1L << T__31) | (1L << T__32) | (1L << LOGICAL) | (1L << ID) | (1L << INT) | (1L << FLOAT) | (1L << STRING))) != 0)) {
					{
					setState(60);
					simple();
					}
				}

				}
				}
				setState(67);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(68);
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
		public Assert_declarationContext assert_declaration() {
			return getRuleContext(Assert_declarationContext.class,0);
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
			setState(73);
			switch (_input.LA(1)) {
			case T__2:
			case T__5:
			case ID:
				enterOuterAlt(_localctx, 1);
				{
				setState(70);
				node_declaration();
				}
				break;
			case T__6:
			case T__7:
			case T__8:
			case T__9:
				enterOuterAlt(_localctx, 2);
				{
				setState(71);
				arc_declaration();
				}
				break;
			case T__11:
				enterOuterAlt(_localctx, 3);
				{
				setState(72);
				assert_declaration();
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
			setState(105);
			switch (_input.LA(1)) {
			case T__2:
				enterOuterAlt(_localctx, 1);
				{
				setState(75);
				((Node_declarationContext)_localctx).node = match(T__2);
				setState(76);
				((Node_declarationContext)_localctx).id = match(ID);
				setState(81);
				_la = _input.LA(1);
				if (_la==T__3) {
					{
					setState(77);
					match(T__3);
					setState(78);
					option_list();
					setState(79);
					match(T__4);
					}
				}

				}
				break;
			case T__5:
				enterOuterAlt(_localctx, 2);
				{
				setState(83);
				((Node_declarationContext)_localctx).node = match(T__5);
				setState(84);
				((Node_declarationContext)_localctx).id = match(ID);
				setState(89);
				_la = _input.LA(1);
				if (_la==T__3) {
					{
					setState(85);
					match(T__3);
					setState(86);
					option_list();
					setState(87);
					match(T__4);
					}
				}

				setState(92);
				_errHandler.sync(this);
				switch ( getInterpreter().adaptivePredict(_input,10,_ctx) ) {
				case 1:
					{
					setState(91);
					simple_block();
					}
					break;
				}
				}
				break;
			case ID:
				enterOuterAlt(_localctx, 3);
				{
				setState(94);
				((Node_declarationContext)_localctx).node = match(ID);
				setState(95);
				((Node_declarationContext)_localctx).id = match(ID);
				setState(100);
				_la = _input.LA(1);
				if (_la==T__3) {
					{
					setState(96);
					match(T__3);
					setState(97);
					option_list();
					setState(98);
					match(T__4);
					}
				}

				setState(103);
				_errHandler.sync(this);
				switch ( getInterpreter().adaptivePredict(_input,12,_ctx) ) {
				case 1:
					{
					setState(102);
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
			setState(107);
			((Arc_declarationContext)_localctx).type = _input.LT(1);
			_la = _input.LA(1);
			if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__6) | (1L << T__7) | (1L << T__8) | (1L << T__9))) != 0)) ) {
				((Arc_declarationContext)_localctx).type = (Token)_errHandler.recoverInline(this);
			} else {
				consume();
			}
			setState(108);
			((Arc_declarationContext)_localctx).srcName = match(ID);
			setState(109);
			match(T__10);
			setState(110);
			((Arc_declarationContext)_localctx).destName = match(ID);
			setState(115);
			_la = _input.LA(1);
			if (_la==T__3) {
				{
				setState(111);
				match(T__3);
				setState(112);
				option_list();
				setState(113);
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

	public static class Assert_declarationContext extends ParserRuleContext {
		public Simple_blockContext simple_block() {
			return getRuleContext(Simple_blockContext.class,0);
		}
		public Assert_declarationContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_assert_declaration; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof JSPNLListener ) ((JSPNLListener)listener).enterAssert_declaration(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof JSPNLListener ) ((JSPNLListener)listener).exitAssert_declaration(this);
		}
	}

	public final Assert_declarationContext assert_declaration() throws RecognitionException {
		Assert_declarationContext _localctx = new Assert_declarationContext(_ctx, getState());
		enterRule(_localctx, 12, RULE_assert_declaration);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(117);
			match(T__11);
			setState(118);
			simple_block();
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
		enterRule(_localctx, 14, RULE_option_list);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(120);
			option_value();
			setState(125);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,15,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					{
					{
					setState(121);
					match(T__12);
					setState(122);
					option_list();
					}
					} 
				}
				setState(127);
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
		enterRule(_localctx, 16, RULE_option_value);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(128);
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
		enterRule(_localctx, 18, RULE_simple);
		try {
			setState(132);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,16,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(130);
				assign_expression();
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(131);
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
		enterRule(_localctx, 20, RULE_assign_expression);
		try {
			setState(149);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,17,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(134);
				((Assign_expressionContext)_localctx).id = match(ID);
				setState(135);
				match(T__13);
				setState(136);
				expression(0);
				 ((Assign_expressionContext)_localctx).type =  1; 
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(139);
				((Assign_expressionContext)_localctx).id = match(ID);
				setState(140);
				match(T__14);
				setState(141);
				expression(0);
				 ((Assign_expressionContext)_localctx).type =  2; 
				}
				break;
			case 3:
				enterOuterAlt(_localctx, 3);
				{
				setState(144);
				ntoken_expression();
				setState(145);
				match(T__13);
				setState(146);
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
		int _startState = 22;
		enterRecursionRule(_localctx, 22, RULE_expression, _p);
		int _la;
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(185);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,18,_ctx) ) {
			case 1:
				{
				setState(152);
				((ExpressionContext)_localctx).op = _input.LT(1);
				_la = _input.LA(1);
				if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__15) | (1L << T__16) | (1L << T__17))) != 0)) ) {
					((ExpressionContext)_localctx).op = (Token)_errHandler.recoverInline(this);
				} else {
					consume();
				}
				setState(153);
				expression(14);
				 ((ExpressionContext)_localctx).type =  1; 
				}
				break;
			case 2:
				{
				setState(156);
				((ExpressionContext)_localctx).op = match(T__30);
				setState(157);
				match(T__3);
				setState(158);
				expression(0);
				setState(159);
				match(T__12);
				setState(160);
				expression(0);
				setState(161);
				match(T__12);
				setState(162);
				expression(0);
				setState(163);
				match(T__4);
				 ((ExpressionContext)_localctx).type =  8; 
				}
				break;
			case 3:
				{
				setState(166);
				function_expression();
				 ((ExpressionContext)_localctx).type =  9; 
				}
				break;
			case 4:
				{
				setState(169);
				ntoken_expression();
				 ((ExpressionContext)_localctx).type =  10; 
				}
				break;
			case 5:
				{
				setState(172);
				enable_expression();
				 ((ExpressionContext)_localctx).type =  14; 
				}
				break;
			case 6:
				{
				setState(175);
				literal_expression();
				 ((ExpressionContext)_localctx).type =  11; 
				}
				break;
			case 7:
				{
				setState(178);
				((ExpressionContext)_localctx).id = match(ID);
				 ((ExpressionContext)_localctx).type =  12; 
				}
				break;
			case 8:
				{
				setState(180);
				match(T__3);
				setState(181);
				expression(0);
				setState(182);
				match(T__4);
				 ((ExpressionContext)_localctx).type =  13; 
				}
				break;
			}
			_ctx.stop = _input.LT(-1);
			setState(219);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,20,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					if ( _parseListeners!=null ) triggerExitRuleEvent();
					_prevctx = _localctx;
					{
					setState(217);
					_errHandler.sync(this);
					switch ( getInterpreter().adaptivePredict(_input,19,_ctx) ) {
					case 1:
						{
						_localctx = new ExpressionContext(_parentctx, _parentState);
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(187);
						if (!(precpred(_ctx, 13))) throw new FailedPredicateException(this, "precpred(_ctx, 13)");
						setState(188);
						((ExpressionContext)_localctx).op = _input.LT(1);
						_la = _input.LA(1);
						if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__18) | (1L << T__19) | (1L << T__20) | (1L << T__21))) != 0)) ) {
							((ExpressionContext)_localctx).op = (Token)_errHandler.recoverInline(this);
						} else {
							consume();
						}
						setState(189);
						expression(14);
						 ((ExpressionContext)_localctx).type =  2; 
						}
						break;
					case 2:
						{
						_localctx = new ExpressionContext(_parentctx, _parentState);
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(192);
						if (!(precpred(_ctx, 12))) throw new FailedPredicateException(this, "precpred(_ctx, 12)");
						setState(193);
						((ExpressionContext)_localctx).op = _input.LT(1);
						_la = _input.LA(1);
						if ( !(_la==T__16 || _la==T__17) ) {
							((ExpressionContext)_localctx).op = (Token)_errHandler.recoverInline(this);
						} else {
							consume();
						}
						setState(194);
						expression(13);
						 ((ExpressionContext)_localctx).type =  3; 
						}
						break;
					case 3:
						{
						_localctx = new ExpressionContext(_parentctx, _parentState);
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(197);
						if (!(precpred(_ctx, 11))) throw new FailedPredicateException(this, "precpred(_ctx, 11)");
						setState(198);
						((ExpressionContext)_localctx).op = _input.LT(1);
						_la = _input.LA(1);
						if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__22) | (1L << T__23) | (1L << T__24) | (1L << T__25))) != 0)) ) {
							((ExpressionContext)_localctx).op = (Token)_errHandler.recoverInline(this);
						} else {
							consume();
						}
						setState(199);
						expression(12);
						 ((ExpressionContext)_localctx).type =  4; 
						}
						break;
					case 4:
						{
						_localctx = new ExpressionContext(_parentctx, _parentState);
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(202);
						if (!(precpred(_ctx, 10))) throw new FailedPredicateException(this, "precpred(_ctx, 10)");
						setState(203);
						((ExpressionContext)_localctx).op = _input.LT(1);
						_la = _input.LA(1);
						if ( !(_la==T__26 || _la==T__27) ) {
							((ExpressionContext)_localctx).op = (Token)_errHandler.recoverInline(this);
						} else {
							consume();
						}
						setState(204);
						expression(11);
						 ((ExpressionContext)_localctx).type =  5; 
						}
						break;
					case 5:
						{
						_localctx = new ExpressionContext(_parentctx, _parentState);
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(207);
						if (!(precpred(_ctx, 9))) throw new FailedPredicateException(this, "precpred(_ctx, 9)");
						setState(208);
						((ExpressionContext)_localctx).op = match(T__28);
						setState(209);
						expression(10);
						 ((ExpressionContext)_localctx).type =  6; 
						}
						break;
					case 6:
						{
						_localctx = new ExpressionContext(_parentctx, _parentState);
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(212);
						if (!(precpred(_ctx, 8))) throw new FailedPredicateException(this, "precpred(_ctx, 8)");
						setState(213);
						((ExpressionContext)_localctx).op = match(T__29);
						setState(214);
						expression(9);
						 ((ExpressionContext)_localctx).type =  7; 
						}
						break;
					}
					} 
				}
				setState(221);
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
		enterRule(_localctx, 24, RULE_function_expression);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(222);
			((Function_expressionContext)_localctx).id = match(ID);
			setState(223);
			match(T__3);
			setState(224);
			arg_list();
			setState(225);
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
		enterRule(_localctx, 26, RULE_arg_list);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(227);
			arg_value();
			setState(232);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,21,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					{
					{
					setState(228);
					match(T__12);
					setState(229);
					arg_list();
					}
					} 
				}
				setState(234);
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
		enterRule(_localctx, 28, RULE_arg_value);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(235);
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
		enterRule(_localctx, 30, RULE_ntoken_expression);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(237);
			match(T__31);
			setState(238);
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
		enterRule(_localctx, 32, RULE_enable_expression);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(240);
			match(T__32);
			setState(241);
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
		enterRule(_localctx, 34, RULE_literal_expression);
		try {
			setState(251);
			switch (_input.LA(1)) {
			case INT:
				enterOuterAlt(_localctx, 1);
				{
				setState(243);
				((Literal_expressionContext)_localctx).val = match(INT);
				 ((Literal_expressionContext)_localctx).type =  1; 
				}
				break;
			case FLOAT:
				enterOuterAlt(_localctx, 2);
				{
				setState(245);
				((Literal_expressionContext)_localctx).val = match(FLOAT);
				 ((Literal_expressionContext)_localctx).type =  2; 
				}
				break;
			case LOGICAL:
				enterOuterAlt(_localctx, 3);
				{
				setState(247);
				((Literal_expressionContext)_localctx).val = match(LOGICAL);
				 ((Literal_expressionContext)_localctx).type =  3; 
				}
				break;
			case STRING:
				enterOuterAlt(_localctx, 4);
				{
				setState(249);
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
		case 11:
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
		"\3\u0430\ud6d1\u8206\uad2d\u4417\uaef1\u8d80\uaadd\3,\u0100\4\2\t\2\4"+
		"\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4\13\t"+
		"\13\4\f\t\f\4\r\t\r\4\16\t\16\4\17\t\17\4\20\t\20\4\21\t\21\4\22\t\22"+
		"\4\23\t\23\3\2\5\2(\n\2\3\2\7\2+\n\2\f\2\16\2.\13\2\3\3\3\3\5\3\62\n\3"+
		"\3\4\7\4\65\n\4\f\4\16\48\13\4\3\4\3\4\5\4<\n\4\3\4\3\4\5\4@\n\4\7\4B"+
		"\n\4\f\4\16\4E\13\4\3\4\3\4\3\5\3\5\3\5\5\5L\n\5\3\6\3\6\3\6\3\6\3\6\3"+
		"\6\5\6T\n\6\3\6\3\6\3\6\3\6\3\6\3\6\5\6\\\n\6\3\6\5\6_\n\6\3\6\3\6\3\6"+
		"\3\6\3\6\3\6\5\6g\n\6\3\6\5\6j\n\6\5\6l\n\6\3\7\3\7\3\7\3\7\3\7\3\7\3"+
		"\7\3\7\5\7v\n\7\3\b\3\b\3\b\3\t\3\t\3\t\7\t~\n\t\f\t\16\t\u0081\13\t\3"+
		"\n\3\n\3\13\3\13\5\13\u0087\n\13\3\f\3\f\3\f\3\f\3\f\3\f\3\f\3\f\3\f\3"+
		"\f\3\f\3\f\3\f\3\f\3\f\5\f\u0098\n\f\3\r\3\r\3\r\3\r\3\r\3\r\3\r\3\r\3"+
		"\r\3\r\3\r\3\r\3\r\3\r\3\r\3\r\3\r\3\r\3\r\3\r\3\r\3\r\3\r\3\r\3\r\3\r"+
		"\3\r\3\r\3\r\3\r\3\r\3\r\3\r\3\r\5\r\u00bc\n\r\3\r\3\r\3\r\3\r\3\r\3\r"+
		"\3\r\3\r\3\r\3\r\3\r\3\r\3\r\3\r\3\r\3\r\3\r\3\r\3\r\3\r\3\r\3\r\3\r\3"+
		"\r\3\r\3\r\3\r\3\r\3\r\3\r\7\r\u00dc\n\r\f\r\16\r\u00df\13\r\3\16\3\16"+
		"\3\16\3\16\3\16\3\17\3\17\3\17\7\17\u00e9\n\17\f\17\16\17\u00ec\13\17"+
		"\3\20\3\20\3\21\3\21\3\21\3\22\3\22\3\22\3\23\3\23\3\23\3\23\3\23\3\23"+
		"\3\23\3\23\5\23\u00fe\n\23\3\23\2\3\30\24\2\4\6\b\n\f\16\20\22\24\26\30"+
		"\32\34\36 \"$\2\b\3\2\t\f\3\2\22\24\3\2\25\30\3\2\23\24\3\2\31\34\3\2"+
		"\35\36\u0113\2,\3\2\2\2\4\61\3\2\2\2\6\66\3\2\2\2\bK\3\2\2\2\nk\3\2\2"+
		"\2\fm\3\2\2\2\16w\3\2\2\2\20z\3\2\2\2\22\u0082\3\2\2\2\24\u0086\3\2\2"+
		"\2\26\u0097\3\2\2\2\30\u00bb\3\2\2\2\32\u00e0\3\2\2\2\34\u00e5\3\2\2\2"+
		"\36\u00ed\3\2\2\2 \u00ef\3\2\2\2\"\u00f2\3\2\2\2$\u00fd\3\2\2\2&(\5\4"+
		"\3\2\'&\3\2\2\2\'(\3\2\2\2()\3\2\2\2)+\7)\2\2*\'\3\2\2\2+.\3\2\2\2,*\3"+
		"\2\2\2,-\3\2\2\2-\3\3\2\2\2.,\3\2\2\2/\62\5\b\5\2\60\62\5\24\13\2\61/"+
		"\3\2\2\2\61\60\3\2\2\2\62\5\3\2\2\2\63\65\7)\2\2\64\63\3\2\2\2\658\3\2"+
		"\2\2\66\64\3\2\2\2\66\67\3\2\2\2\679\3\2\2\28\66\3\2\2\29;\7\3\2\2:<\5"+
		"\24\13\2;:\3\2\2\2;<\3\2\2\2<C\3\2\2\2=?\7)\2\2>@\5\24\13\2?>\3\2\2\2"+
		"?@\3\2\2\2@B\3\2\2\2A=\3\2\2\2BE\3\2\2\2CA\3\2\2\2CD\3\2\2\2DF\3\2\2\2"+
		"EC\3\2\2\2FG\7\4\2\2G\7\3\2\2\2HL\5\n\6\2IL\5\f\7\2JL\5\16\b\2KH\3\2\2"+
		"\2KI\3\2\2\2KJ\3\2\2\2L\t\3\2\2\2MN\7\5\2\2NS\7%\2\2OP\7\6\2\2PQ\5\20"+
		"\t\2QR\7\7\2\2RT\3\2\2\2SO\3\2\2\2ST\3\2\2\2Tl\3\2\2\2UV\7\b\2\2V[\7%"+
		"\2\2WX\7\6\2\2XY\5\20\t\2YZ\7\7\2\2Z\\\3\2\2\2[W\3\2\2\2[\\\3\2\2\2\\"+
		"^\3\2\2\2]_\5\6\4\2^]\3\2\2\2^_\3\2\2\2_l\3\2\2\2`a\7%\2\2af\7%\2\2bc"+
		"\7\6\2\2cd\5\20\t\2de\7\7\2\2eg\3\2\2\2fb\3\2\2\2fg\3\2\2\2gi\3\2\2\2"+
		"hj\5\6\4\2ih\3\2\2\2ij\3\2\2\2jl\3\2\2\2kM\3\2\2\2kU\3\2\2\2k`\3\2\2\2"+
		"l\13\3\2\2\2mn\t\2\2\2no\7%\2\2op\7\r\2\2pu\7%\2\2qr\7\6\2\2rs\5\20\t"+
		"\2st\7\7\2\2tv\3\2\2\2uq\3\2\2\2uv\3\2\2\2v\r\3\2\2\2wx\7\16\2\2xy\5\6"+
		"\4\2y\17\3\2\2\2z\177\5\22\n\2{|\7\17\2\2|~\5\20\t\2}{\3\2\2\2~\u0081"+
		"\3\2\2\2\177}\3\2\2\2\177\u0080\3\2\2\2\u0080\21\3\2\2\2\u0081\177\3\2"+
		"\2\2\u0082\u0083\5\26\f\2\u0083\23\3\2\2\2\u0084\u0087\5\26\f\2\u0085"+
		"\u0087\5\30\r\2\u0086\u0084\3\2\2\2\u0086\u0085\3\2\2\2\u0087\25\3\2\2"+
		"\2\u0088\u0089\7%\2\2\u0089\u008a\7\20\2\2\u008a\u008b\5\30\r\2\u008b"+
		"\u008c\b\f\1\2\u008c\u0098\3\2\2\2\u008d\u008e\7%\2\2\u008e\u008f\7\21"+
		"\2\2\u008f\u0090\5\30\r\2\u0090\u0091\b\f\1\2\u0091\u0098\3\2\2\2\u0092"+
		"\u0093\5 \21\2\u0093\u0094\7\20\2\2\u0094\u0095\5\30\r\2\u0095\u0096\b"+
		"\f\1\2\u0096\u0098\3\2\2\2\u0097\u0088\3\2\2\2\u0097\u008d\3\2\2\2\u0097"+
		"\u0092\3\2\2\2\u0098\27\3\2\2\2\u0099\u009a\b\r\1\2\u009a\u009b\t\3\2"+
		"\2\u009b\u009c\5\30\r\20\u009c\u009d\b\r\1\2\u009d\u00bc\3\2\2\2\u009e"+
		"\u009f\7!\2\2\u009f\u00a0\7\6\2\2\u00a0\u00a1\5\30\r\2\u00a1\u00a2\7\17"+
		"\2\2\u00a2\u00a3\5\30\r\2\u00a3\u00a4\7\17\2\2\u00a4\u00a5\5\30\r\2\u00a5"+
		"\u00a6\7\7\2\2\u00a6\u00a7\b\r\1\2\u00a7\u00bc\3\2\2\2\u00a8\u00a9\5\32"+
		"\16\2\u00a9\u00aa\b\r\1\2\u00aa\u00bc\3\2\2\2\u00ab\u00ac\5 \21\2\u00ac"+
		"\u00ad\b\r\1\2\u00ad\u00bc\3\2\2\2\u00ae\u00af\5\"\22\2\u00af\u00b0\b"+
		"\r\1\2\u00b0\u00bc\3\2\2\2\u00b1\u00b2\5$\23\2\u00b2\u00b3\b\r\1\2\u00b3"+
		"\u00bc\3\2\2\2\u00b4\u00b5\7%\2\2\u00b5\u00bc\b\r\1\2\u00b6\u00b7\7\6"+
		"\2\2\u00b7\u00b8\5\30\r\2\u00b8\u00b9\7\7\2\2\u00b9\u00ba\b\r\1\2\u00ba"+
		"\u00bc\3\2\2\2\u00bb\u0099\3\2\2\2\u00bb\u009e\3\2\2\2\u00bb\u00a8\3\2"+
		"\2\2\u00bb\u00ab\3\2\2\2\u00bb\u00ae\3\2\2\2\u00bb\u00b1\3\2\2\2\u00bb"+
		"\u00b4\3\2\2\2\u00bb\u00b6\3\2\2\2\u00bc\u00dd\3\2\2\2\u00bd\u00be\f\17"+
		"\2\2\u00be\u00bf\t\4\2\2\u00bf\u00c0\5\30\r\20\u00c0\u00c1\b\r\1\2\u00c1"+
		"\u00dc\3\2\2\2\u00c2\u00c3\f\16\2\2\u00c3\u00c4\t\5\2\2\u00c4\u00c5\5"+
		"\30\r\17\u00c5\u00c6\b\r\1\2\u00c6\u00dc\3\2\2\2\u00c7\u00c8\f\r\2\2\u00c8"+
		"\u00c9\t\6\2\2\u00c9\u00ca\5\30\r\16\u00ca\u00cb\b\r\1\2\u00cb\u00dc\3"+
		"\2\2\2\u00cc\u00cd\f\f\2\2\u00cd\u00ce\t\7\2\2\u00ce\u00cf\5\30\r\r\u00cf"+
		"\u00d0\b\r\1\2\u00d0\u00dc\3\2\2\2\u00d1\u00d2\f\13\2\2\u00d2\u00d3\7"+
		"\37\2\2\u00d3\u00d4\5\30\r\f\u00d4\u00d5\b\r\1\2\u00d5\u00dc\3\2\2\2\u00d6"+
		"\u00d7\f\n\2\2\u00d7\u00d8\7 \2\2\u00d8\u00d9\5\30\r\13\u00d9\u00da\b"+
		"\r\1\2\u00da\u00dc\3\2\2\2\u00db\u00bd\3\2\2\2\u00db\u00c2\3\2\2\2\u00db"+
		"\u00c7\3\2\2\2\u00db\u00cc\3\2\2\2\u00db\u00d1\3\2\2\2\u00db\u00d6\3\2"+
		"\2\2\u00dc\u00df\3\2\2\2\u00dd\u00db\3\2\2\2\u00dd\u00de\3\2\2\2\u00de"+
		"\31\3\2\2\2\u00df\u00dd\3\2\2\2\u00e0\u00e1\7%\2\2\u00e1\u00e2\7\6\2\2"+
		"\u00e2\u00e3\5\34\17\2\u00e3\u00e4\7\7\2\2\u00e4\33\3\2\2\2\u00e5\u00ea"+
		"\5\36\20\2\u00e6\u00e7\7\17\2\2\u00e7\u00e9\5\34\17\2\u00e8\u00e6\3\2"+
		"\2\2\u00e9\u00ec\3\2\2\2\u00ea\u00e8\3\2\2\2\u00ea\u00eb\3\2\2\2\u00eb"+
		"\35\3\2\2\2\u00ec\u00ea\3\2\2\2\u00ed\u00ee\5\30\r\2\u00ee\37\3\2\2\2"+
		"\u00ef\u00f0\7\"\2\2\u00f0\u00f1\7%\2\2\u00f1!\3\2\2\2\u00f2\u00f3\7#"+
		"\2\2\u00f3\u00f4\7%\2\2\u00f4#\3\2\2\2\u00f5\u00f6\7&\2\2\u00f6\u00fe"+
		"\b\23\1\2\u00f7\u00f8\7\'\2\2\u00f8\u00fe\b\23\1\2\u00f9\u00fa\7$\2\2"+
		"\u00fa\u00fe\b\23\1\2\u00fb\u00fc\7(\2\2\u00fc\u00fe\b\23\1\2\u00fd\u00f5"+
		"\3\2\2\2\u00fd\u00f7\3\2\2\2\u00fd\u00f9\3\2\2\2\u00fd\u00fb\3\2\2\2\u00fe"+
		"%\3\2\2\2\31\',\61\66;?CKS[^fiku\177\u0086\u0097\u00bb\u00db\u00dd\u00ea"+
		"\u00fd";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}