/*
 * Copyright (c) 2001-2007, TIBCO Software Inc.
 * Use, modification, and distribution subject to terms of license.
 */
jsx3.require("jsx3.gui.Block","jsx3.gui.Tab");jsx3.Class.defineClass("jsx3.gui.TabbedPane",jsx3.gui.Block,null,function(s,k){var V=jsx3.gui.Tab;var pc=jsx3.gui.Event;var Bc=jsx3.gui.Block;var Mc=jsx3.gui.Interactive;s.DEFAULTTABHEIGHT=20;k.init=function(l){this.jsxsuper(l);};k.paintChild=function(i,q){var mb=this.getRendered();if(mb!=null){if(this.getShowTabs())jsx3.html.insertAdjacentHTML(mb.childNodes[0],"beforeEnd",i.paint());var Pb=i.getContentChild();var Cb=this.getChildren().length==1;Pb.setDisplay(Cb?Bc.DISPLAYBLOCK:Bc.DISPLAYNONE);jsx3.html.insertAdjacentHTML(mb,"beforeEnd",i.paintChildren([Pb]));if(Cb)this.Bv(null,i);}};k.onSetChild=function(r){if(!(r instanceof V))return false;var Ec=r.getContentChild();if(Ec)Ec.setVisibility(Bc.VISIBILITYVISIBLE);if(this.getChildren().length==0)this.jsxselectedindex=-1;return true;};k.onRemoveChild=function(c,i){this.jsxsuper(c,i);if(c instanceof Array){var _=c[this.getSelectedIndex()];if(_)_.doEvent(Mc.HIDE);this.doEvent(Mc.CHANGE);this.setSelectedIndex(-1);this.C5();this.repaint();}else{var B=this.getSelectedIndex();var sb=Math.min(this.getChildren().length-1,B);if(B==i)c.doEvent(Mc.HIDE);this.Bv(false,sb,true);}};k.getSelectedIndex=function(){return this.jsxselectedindex==null?this.getChildren().length>0?0:-1:this.jsxselectedindex;};k.setSelectedIndex=function(m,r){if(r){this.Bv(false,m);}else{this.jsxselectedindex=m instanceof V?m.getChildIndex():m;}return this;};k._4=function(b,q){this.doDrop(b,q,jsx3.EventHelp.ONDROP);};k.IO=function(){if(this.getParent()){var Lb=this.getParent().IO(this);var D=Lb.width?Lb.width:Lb.parentwidth;var Pb=this.getShowTabs()?this.paintTabSize()+1:0;return {parentwidth:D,parentheight:Pb};}return {};};k.Ru=function(){if(this.getParent()){var t=this.getParent().IO(this);var Kc=t.width!=null&&!isNaN(t.width)?t.width:t.parentwidth;var Ac=this.getShowTabs()?this.paintTabSize():0;var fb=(t.height!=null&&!isNaN(t.height)?t.height:t.parentheight)-Ac;var N={left:0,top:Ac,width:Kc,height:fb,parentwidth:Kc,parentheight:fb,boxtype:"box",tagname:"div"};if(this.getShowTabs())N.border="solid 1px #f6f6ff;solid 1px #a6a6af;solid 1px #a6a6af;solid 1px #f6f6ff";return N;}return {};};k.k7=function(n,q,d){var rc=this.RL(true,n);if(q){rc.recalculate(n,q,d);var B=rc.pQ(0);B.recalculate({parentwidth:rc.XK(),height:this.paintTabSize()+1},q!=null?q.childNodes[0]:null,d);var vb=this.getChildren();var L=this.IO();var wc=this.Ru();for(var Eb=0;Eb<vb.length;Eb++){var kc=vb[Eb];d.add(kc,this.IO(),q!=null,true);var Db=kc.getContentChild();if(Db!=null){var y=this.getSelectedIndex()==Eb;Db.setDisplay(y?Bc.DISPLAYBLOCK:Bc.DISPLAYNONE,true);if(y)d.add(Db,this.Ru(),Db.getRendered(q),true);}}}};k.T5=function(e){if(this.getParent()&&(e==null||!isNaN(e.parentwidth)&&!isNaN(e.parentheight)||!isNaN(e.width)&&!isNaN(e.height))){e=this.getParent().IO(this);}else{if(e==null){e={};}}if(e.left==null)e.left=0;if(e.top==null)e.top=0;if(e.width==null)e.width="100%";if(e.height==null)e.height="100%";if(e.tagname==null)e.tagname="div";if(e.boxtype==null)e.boxtype="box";var S=this.getBorder();if(S!=null&&S!="")e.border=S;var Q=new jsx3.gui.Painted.Box(e);var fc={};fc.parentwidth=e.parentwidth;fc.width="100%";fc.height=this.paintTabSize()+1;fc.left=0;fc.top=0;fc.tagname="div";fc.boxtype="box";var Fb=new jsx3.gui.Painted.Box(fc);Q.W8(Fb);return Q;};k.paint=function(){this.applyDynamicProperties();var Jc=this.getId();var Dc=this.getShowTabs();var D=this.getSelectedIndex();if(D<0||D>=this.getChildren().length){D=0;this.setSelectedIndex(D);}var L={};if(this.hasEvent(Mc.DROP)||this.hasEvent(Mc.CTRL_DROP))L[pc.MOUSEUP]=true;var jb=this.lM(L,0);var hc=this.renderAttributes(null,true);var kb=this.RL(true);kb.setAttributes("id=\""+Jc+"\" label=\""+this.getName()+"\" "+this.CI()+this.vH()+jb+hc);kb.setStyles("overflow:hidden;"+this.d9()+this.UZ()+this.K2()+this.T1()+this.MU()+this.iN());var qb=kb.pQ(0);if(Dc){var ac=this.getChild(D);ac=ac==null?this.getChild(0):ac;if(ac!=null){if(!ac.getEnabled()){var bc=this.getChildren().length-1;for(var cb=0;cb<=bc;cb++){if(this.getChild(cb).getEnabled()){this.setSelectedIndex(cb);break;}}}}var J=this.paintChildren();qb.setStyles(this.eQ());}else{var J="&#160;";qb.setStyles("visibility:hidden;");}qb.setAttributes("class=\"jsx30tabbedpane_controlbox\"");var Ec=this.getChildren();var wc=[];for(var cb=0;cb<Ec.length;cb++){var Fc=Ec[cb].getContentChild();if(Fc!=null){Fc.setDisplay(this.getSelectedIndex()==cb?Bc.DISPLAYBLOCK:Bc.DISPLAYNONE);Fc.J1(this.Ru());wc.push(Fc);}}return kb.paint().join(qb.paint().join(J)+this.paintChildren(wc));};k.CI=function(){return this.jsxsuper(this.getIndex()||Number(0));};k.paintTabSize=function(){return this.getTabHeight()!=null&&!isNaN(this.getTabHeight())?this.getTabHeight():s.DEFAULTTABHEIGHT;};k.getTabHeight=function(){return this.jsxtabheight;};k.setTabHeight=function(c){this.jsxtabheight=c;this.vQ(true);return this;};k.getShowTabs=function(){return this.jsxshowtabs==null||this.jsxshowtabs===""?1:this.jsxshowtabs;};k.setShowTabs=function(o){this.jsxshowtabs=o;this.vQ(true);return this;};k.Bv=function(b,c,o,p){if(!(c instanceof V))c=this.getChild(c);if(c){var Fb=this.getShowTabs();var Ec=c.getChildIndex();var mb=this.getSelectedIndex();if(o||mb!=Ec){this.setSelectedIndex(Ec);var Jb=this.getChildren().length;for(var Gb=0;Gb<Jb;Gb++){var D=this.getChild(Gb);var nc=D.getContentChild();if(Gb==Ec){if(nc)nc.setDisplay(Bc.DISPLAYBLOCK,true);if(Fb)D.setBackgroundColor(D.C3(),true);nc.nW(this.Ru(),true);}else{if(nc)nc.setDisplay(Bc.DISPLAYNONE,true);if(Fb)D.setBackgroundColor(D.m_(),true);}}}if(b)this.doEvent(Mc.EXECUTE,{objEVENT:b instanceof pc?b:null});if(!o){var W=this.getChild(mb);if(W)W.doEvent(Mc.HIDE);}if(p)c.focus();c.doEvent(Mc.SHOW);this.doEvent(Mc.CHANGE);}};s.getVersion=function(){return "3.0.00";};});jsx3.TabbedPane=jsx3.gui.TabbedPane;
