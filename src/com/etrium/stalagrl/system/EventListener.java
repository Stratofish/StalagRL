package com.etrium.stalagrl.system;

public interface EventListener
{
  public boolean ReceiveEvent(EtriumEvent p_event);
  void StartListening();
  void StopListening();
}
